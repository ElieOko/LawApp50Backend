package emy.backend.lawapp50.app.evaluation.domain.model

import emy.backend.lawapp50.app.evaluation.domain.request.QuestionCaseStudyRequest
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionCaseStudyRequestDAO
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionOptionRequest
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionOptionRequestDAO
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionOuverteRequest
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionOuverteRequestDAO
import emy.backend.lawapp50.app.evaluation.domain.request.QuestionRequest
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import org.jetbrains.annotations.NotNull
import java.time.*

class Evaluation(
    val id: Long? = null,
    val title : String,
    val description : String,
    var fileContent : String? =  null,
    val userId : Long,
    val compteur : Long? = 0,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

fun Evaluation.toEntity() = EvaluationEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    fileContent = this.fileContent,
    userId = this.userId,
    compteur = this.compteur,
    startDate = this.startDate,
    endDate = this.endDate
)

data class EvaluationDAO(
    val id : Long? = null, val title : String,
    val description : String, val compteur : Long? = null,
    var fileContent : String? =  null, val startDate: LocalDate,
    val endDate: LocalDate,
    val option : List<QuestionOptionDAO>? = emptyList(),
    val ouverte : List<QuestionOuverteDAO>? = emptyList(),
    val caseStudy : List<QuestionCaseStudyDAO>? = emptyList(),
)

data class QuestionOptionDAO(
    val question :Question?= null,
    val questionOption : List<QuestionOption> = emptyList()
)
data class QuestionOuverteDAO(
    val question :Question?= null,
    val questionOuverte : List<QuestionOuverte> = emptyList()
)

data class QuestionCaseStudyDAO(
    val question :Question? = null,
    val questionCaseStudy : List<QuestionCaseStudy> = emptyList()
)