package emy.backend.lawapp50.app.evaluation.application.service

import com.google.protobuf.LazyStringArrayList.emptyList
import emy.backend.lawapp50.app.evaluation.domain.model.*
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.*
import org.springframework.web.server.ResponseStatusException
import kotlin.collections.emptyList
import kotlin.collections.get
import kotlin.collections.map

@Service
class EvaluationService (
    private val repository : EvaluationRepository,
    private val questionRepository: QuestionRepository,
    private val optionRepository: QuestionOptionRepository,
    private val caseStudyRepository: QuestionCaseStudyRepository,
    private val ouverteRepository: QuestionOuverteRepository
) {
    suspend fun create(model : Evaluation) = coroutineScope {
        repository.save(model.toEntity())
    }
    suspend fun findById(id : Long) = coroutineScope {
        val data = repository.findById(id)?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID Is Not Found.")
        data.toDomain()
    }

    suspend fun getAllData() = coroutineScope {
        val dataEvaluation = repository.findAll().toList()
        getAllDataCompact(dataEvaluation)
    }
    suspend fun showDetail(id: Long) = coroutineScope{
        val dataEvaluation = repository.findById(id)?: throw ResponseStatusException(HttpStatus.valueOf(404), "ID Is Not Found.")
        getAllDataCompact(listOf(dataEvaluation))
    }
    private suspend fun getAllDataCompact(dataEvaluation : List<EvaluationEntity>) = coroutineScope{
        val evaluations = mutableListOf<EvaluationDAO>()
        val evaluationIds: List<Long> = dataEvaluation.map { it.id!! }
        val questions = questionRepository.findByEvaluationIdIn(evaluationIds).toList()
        val questionIds : List<Long> = questions.map { it.id!!}
        dataEvaluation.forEach { data ->
            val q = questionRepository.findByEvaluationIdIn(listOf(data.id!!)).toList()
            val optionsByQuestion = optionRepository
                .findByQuestionIdIn(questionIds).toList()
                .groupBy { it.questionId }
            val ouvertByQuestion = ouverteRepository
                .findByQuestionIdIn(questionIds).toList()
                .groupBy { it.questionId }
            val caseStudyByQuestion = caseStudyRepository
                .findByQuestionIdIn(questionIds).toList()
                .groupBy { it.questionId }

            val selectQ = q.mapNotNull { qe ->
                val test = optionsByQuestion[qe.id]
                    ?.map { it.toDomain() }
                    ?: return@mapNotNull null
                QuestionOptionDAO(qe.toDomain(), test)
            }
            val openQ = q.mapNotNull { qe ->
                val test = ouvertByQuestion[qe.id]
                    ?.map { it.toDomain() }
                    ?: return@mapNotNull null
                QuestionOuverteDAO(qe.toDomain(), test)
            }
            val caseQ = q.mapNotNull { qe ->
                val test = caseStudyByQuestion[qe.id]
                    ?.map { it.toDomain() }
                    ?: return@mapNotNull null
                QuestionCaseStudyDAO(qe.toDomain(), test)
            }

            val items = EvaluationDAO(
                id = data.id,
                title = data.title,
                description = data.description,
                compteur = data.compteur,
                fileContent = data.fileContent,
                startDate = data.startDate,
                endDate = data.endDate,
                option = selectQ,
                ouverte = openQ,
                caseStudy = caseQ,
            )
            evaluations.add(items)
        }
        evaluations
    }
}