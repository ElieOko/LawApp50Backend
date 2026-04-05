package emy.backend.lawapp50.app.quiz.application.service

import emy.backend.lawapp50.app.quiz.domain.model.*
import emy.backend.lawapp50.app.quiz.domain.request.QuizRequest
import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.quiz.infrastructure.persistance.repository.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val levelRepository: QuizLevelRepository,
    private val questionRepository: QuizQuestionRepository,
    private val optionRepository: QuizQuestionOptionRepository,
) {
    suspend fun createFromRequest(userId: Long, body: QuizRequest): QuizDetail = coroutineScope {
        validateQuizStructure(body)
        val savedQuiz = quizRepository.save(
            QuizEntity(
                title = body.title,
                description = body.description,
                userId = userId,
                isActive = true,
            ),
        )
        val quizId = savedQuiz.id
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Quiz non persisté.")
        val sortedLevels = body.levels.sortedBy { it.levelOrder }
        for (levelReq in sortedLevels) {
            val level = levelRepository.save(
                QuizLevelEntity(
                    quizId = quizId,
                    title = levelReq.title,
                    levelOrder = levelReq.levelOrder,
                    isActive = true,
                ),
            )
            val levelId = level.id
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Niveau non persisté.")
            for (q in levelReq.questions) {
                val question = questionRepository.save(
                    QuizQuestionEntity(
                        quizLevelId = levelId,
                        title = q.title,
                        point = q.point,
                        isActive = true,
                    ),
                )
                val questionId = question.id
                    ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Question non persistée.")
                for (opt in q.options) {
                    optionRepository.save(
                        QuizQuestionOptionEntity(
                            questionId = questionId,
                            optionText = opt.option,
                            isValid = opt.isGoal,
                            isActive = true,
                        ),
                    )
                }
            }
        }
        loadDetail(quizId)
    }

    suspend fun findDetail(quizId: Long): QuizDetail = coroutineScope {
        loadDetail(quizId)
    }

    suspend fun listActive(): List<Quiz> = coroutineScope {
        quizRepository.findAllByIsActiveTrueOrderByCreatedAtDesc()
            .map { it.toDomain() }
            .toList()
    }

    private suspend fun loadDetail(quizId: Long): QuizDetail {
        val quiz = quizRepository.findById(quizId)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "Quiz introuvable.")
        val q = quiz.toDomain()
        val levels = levelRepository.findAllByQuizIdAndIsActiveTrueOrderByLevelOrderAsc(quizId).toList()
        val levelNodes = levels.map { level ->
            val levelId = level.id
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Niveau invalide.")
            val questions = questionRepository.findAllByQuizLevelIdAndIsActiveTrueOrderByIdAsc(levelId).toList()
            QuizLevelNode(
                id = level.id,
                title = level.title,
                levelOrder = level.levelOrder,
                questions = questions.map { question ->
                    val questionId = question.id
                        ?: throw ResponseStatusException(HttpStatusCode.valueOf(500), "Question invalide.")
                    val options = optionRepository.findAllByQuestionIdAndIsActiveTrueOrderByIdAsc(questionId).toList()
                    QuizQuestionNode(
                        id = question.id,
                        title = question.title,
                        point = question.point,
                        options = options.map { opt ->
                            QuizOptionNode(
                                id = opt.id,
                                option = opt.optionText,
                                isValid = opt.isValid,
                            )
                        },
                    )
                },
            )
        }
        return QuizDetail(
            id = q.id,
            title = q.title,
            description = q.description,
            userId = q.userId,
            isActive = q.isActive,
            createdAt = q.createdAt,
            levels = levelNodes,
        )
    }

    private fun validateQuizStructure(body: QuizRequest) {
        if (body.levels.isEmpty()) {
            throw ResponseStatusException(
                HttpStatusCode.valueOf(400),
                "Le quiz doit contenir au moins un niveau.",
            )
        }
        for (level in body.levels) {
            if (level.questions.isEmpty()) {
                throw ResponseStatusException(
                    HttpStatusCode.valueOf(400),
                    "Chaque niveau doit contenir au moins une question.",
                )
            }
            for (question in level.questions) {
                if (question.options.size < 2) {
                    throw ResponseStatusException(
                        HttpStatusCode.valueOf(400),
                        "Chaque question doit proposer au moins deux réponses.",
                    )
                }
                val correct = question.options.count { it.isGoal }
                if (correct != 1) {
                    throw ResponseStatusException(
                        HttpStatusCode.valueOf(400),
                        "Chaque question doit avoir exactement une bonne réponse (isGoal = true).",
                    )
                }
            }
        }
    }
}
