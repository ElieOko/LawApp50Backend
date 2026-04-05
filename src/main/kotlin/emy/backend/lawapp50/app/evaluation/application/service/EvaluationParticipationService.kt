package emy.backend.lawapp50.app.evaluation.application.service

import emy.backend.lawapp50.app.evaluation.domain.model.*
import emy.backend.lawapp50.app.evaluation.domain.request.EvaluationAnswerSubmitRequest
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionAnswerItemRequest
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationSubmissionAnswerEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationSubmissionEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDate

@Service
class EvaluationParticipationService(
    private val evaluationRepository: EvaluationRepository,
    private val questionRepository: QuestionRepository,
    private val questionOptionRepository: QuestionOptionRepository,
    private val questionOuverteRepository: QuestionOuverteRepository,
    private val questionCaseStudyRepository: QuestionCaseStudyRepository,
    private val submissionRepository: EvaluationSubmissionRepository,
    private val submissionAnswerRepository: EvaluationSubmissionAnswerRepository,
) {
    private enum class QuestionKind { OPTION, OUVERTE, CASE_STUDY, NONE }

    suspend fun listAnswerableForUser(userId: Long): List<EvaluationAnswerableSummary> = coroutineScope {
        val today = LocalDate.now()
        evaluationRepository.findAnswerableForRespondent(userId, today)
            .toList()
            .map { e ->
                val id = e.id ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Évaluation invalide.")
                EvaluationAnswerableSummary(
                    id = id,
                    title = e.title,
                    description = e.description,
                    startDate = e.startDate,
                    endDate = e.endDate,
                )
            }
    }

    suspend fun buildPassage(evaluationId: Long, respondentUserId: Long): EvaluationPassageView = coroutineScope {
        val evaluation = evaluationRepository.findById(evaluationId)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "Évaluation introuvable.")
        assertRespondentAllowed(evaluation, respondentUserId)
        val evalId = evaluation.id ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Évaluation invalide.")
        val questions = questionRepository.findAllByEvaluationIdAndIsActiveTrueOrderByIdAsc(evalId).toList()
        val passageQuestions = questions.map { q ->
            val qid = q.id ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Question invalide.")
            when (resolveKind(qid)) {
                QuestionKind.OPTION -> {
                    val opts = questionOptionRepository.findAllByQuestionIdAndIsActiveTrueOrderByIdAsc(qid).toList()
                    EvaluationQuestionPassageView(
                        id = qid,
                        title = q.title,
                        point = q.point,
                        kind = "OPTION",
                        options = opts.mapNotNull { o ->
                            val oid = o.id ?: return@mapNotNull null
                            EvaluationOptionPassageView(id = oid, option = o.option)
                        },
                        promptTitle = null,
                        promptFileContent = null,
                    )
                }
                QuestionKind.OUVERTE -> {
                    val row = questionOuverteRepository.findAllByQuestionIdAndIsActiveTrue(qid).toList().firstOrNull()
                    EvaluationQuestionPassageView(
                        id = qid,
                        title = q.title,
                        point = q.point,
                        kind = "OUVERTE",
                        options = null,
                        promptTitle = row?.title,
                        promptFileContent = row?.fileContent,
                    )
                }
                QuestionKind.CASE_STUDY -> {
                    val row = questionCaseStudyRepository.findAllByQuestionIdAndIsActiveTrue(qid).toList().firstOrNull()
                    EvaluationQuestionPassageView(
                        id = qid,
                        title = q.title,
                        point = q.point,
                        kind = "CASE_STUDY",
                        options = null,
                        promptTitle = row?.title,
                        promptFileContent = row?.fileContent,
                    )
                }
                QuestionKind.NONE -> throw ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Question sans contenu répondable (id=$qid).",
                )
            }
        }
        EvaluationPassageView(
            id = evalId,
            title = evaluation.title,
            description = evaluation.description,
            startDate = evaluation.startDate,
            endDate = evaluation.endDate,
            questions = passageQuestions,
        )
    }

    suspend fun submitAnswers(
        evaluationId: Long,
        respondentUserId: Long,
        body: EvaluationAnswerSubmitRequest,
    ): EvaluationSubmitResult = coroutineScope {
        val evaluation = evaluationRepository.findById(evaluationId)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "Évaluation introuvable.")
        assertRespondentAllowed(evaluation, respondentUserId)
        val evalId = evaluation.id ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Évaluation invalide.")

        if (submissionRepository.existsByEvaluationIdAndUserId(evalId, respondentUserId)) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(409),
                "Vous avez déjà répondu à cette évaluation.",
            )
        }

        val questions = questionRepository.findAllByEvaluationIdAndIsActiveTrueOrderByIdAsc(evalId).toList()
        if (questions.isEmpty()) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Cette évaluation ne contient aucune question.")
        }

        val expectedIds = questions.mapNotNull { it.id }.toSet()
        val answersByQuestion = body.answers.associateBy { it.questionId }
        if (answersByQuestion.size != expectedIds.size || answersByQuestion.keys != expectedIds) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Vous devez fournir exactement une réponse par question de l'évaluation.",
            )
        }

        var scoreQcm = 0.0
        var maxQcmPoints = 0.0
        val rowsToSave = mutableListOf<Triple<Long, QuestionAnswerItemRequest, QuestionKind>>()

        for (question in questions) {
            val qid = question.id ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Question invalide.")
            val answer = answersByQuestion[qid]!!
            val kind = resolveKind(qid)
            when (kind) {
                QuestionKind.OPTION -> {
                    maxQcmPoints += question.point
                    val selected = answer.selectedOptionId ?: throw ResponseStatusException(
                        HttpStatusCode.valueOf(400),
                        "Réponse QCM manquante pour la question $qid.",
                    )
                    if (answer.textResponse != null) {
                        throw ResponseStatusException(
                            HttpStatusCode.valueOf(400),
                            "Réponse texte non attendue pour la question QCM $qid.",
                        )
                    }
                    val option = questionOptionRepository.findById(selected)
                        ?: throw ResponseStatusException(HttpStatusCode.valueOf(400), "Option de réponse invalide.")
                    if (option.questionId != qid) {
                        throw ResponseStatusException(HttpStatusCode.valueOf(400), "Option de réponse invalide pour cette question.")
                    }
                    if (option.isValid) {
                        scoreQcm += question.point
                    }
                    rowsToSave.add(Triple(qid, answer, kind))
                }
                QuestionKind.OUVERTE, QuestionKind.CASE_STUDY -> {
                    val text = answer.textResponse?.trim().orEmpty()
                    if (text.isEmpty()) {
                        throw ResponseStatusException(
                            HttpStatusCode.valueOf(400),
                            "Réponse texte obligatoire pour la question $qid.",
                        )
                    }
                    if (answer.selectedOptionId != null) {
                        throw ResponseStatusException(
                            HttpStatusCode.valueOf(400),
                            "Réponse QCM non attendue pour la question $qid.",
                        )
                    }
                    rowsToSave.add(Triple(qid, answer, kind))
                }
                QuestionKind.NONE -> throw ResponseStatusException(
                    HttpStatusCode.valueOf(500),
                    "Question sans type (id=$qid).",
                )
            }
        }

        val submission = submissionRepository.save(
            EvaluationSubmissionEntity(
                evaluationId = evalId,
                userId = respondentUserId,
            ),
        )
        val submissionId = submission.id
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Soumission non persistée.")

        for ((qid, answer, kind) in rowsToSave) {
            submissionAnswerRepository.save(
                EvaluationSubmissionAnswerEntity(
                    submissionId = submissionId,
                    questionId = qid,
                    selectedOptionId = if (kind == QuestionKind.OPTION) answer.selectedOptionId else null,
                    textResponse = if (kind == QuestionKind.OPTION) null else answer.textResponse?.trim(),
                ),
            )
        }

        EvaluationSubmitResult(
            submissionId = submissionId,
            scoreQcm = scoreQcm,
            maxQcmPoints = maxQcmPoints,
            message = "Réponses enregistrées. Les questions ouvertes et cas pratiques seront corrigées séparément.",
        )
    }

    private fun assertRespondentAllowed(evaluation: EvaluationEntity, respondentUserId: Long) {
        if (!evaluation.isActive) {
            throw ResponseStatusException(HttpStatusCode.valueOf(400), "Cette évaluation n'est plus active.")
        }
        if (evaluation.userId == respondentUserId) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(403),
                "Le créateur de l'évaluation ne peut pas y répondre lui-même.",
            )
        }
        val today = LocalDate.now()
        if (today.isBefore(evaluation.startDate) || today.isAfter(evaluation.endDate)) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "L'évaluation n'est pas ouverte à cette date.",
            )
        }
    }

    private suspend fun resolveKind(questionId: Long): QuestionKind {
        if (questionOptionRepository.findAllByQuestionIdAndIsActiveTrueOrderByIdAsc(questionId).toList().isNotEmpty()) {
            return QuestionKind.OPTION
        }
        if (questionOuverteRepository.findAllByQuestionIdAndIsActiveTrue(questionId).toList().isNotEmpty()) {
            return QuestionKind.OUVERTE
        }
        if (questionCaseStudyRepository.findAllByQuestionIdAndIsActiveTrue(questionId).toList().isNotEmpty()) {
            return QuestionKind.CASE_STUDY
        }
        return QuestionKind.NONE
    }
}
