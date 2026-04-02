package emy.backend.lawapp50.app.evaluation.domain.request

import emy.backend.lawapp50.app.evaluation.domain.model.Evaluation
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class EvaluationRequest(
    @NotNull
    val title : String,
    val description : String,
    val compteur : Long? = null,
    var fileContent : String? =  null,
    @NotNull
    val startDate: LocalDate,
    @NotNull
    val endDate: LocalDate,
    val questionOption : Map<QuestionRequest,List<QuestionOptionRequest>>?,
    val questionOuverte : Map<QuestionRequest,List<QuestionOuverteRequest>>?,
    val questionCaseStudy : Map<QuestionRequest,List<QuestionCaseStudyRequest>>?
)

fun EvaluationRequest.toDomain(userId : Long) = Evaluation(
    title = this.title,
    description = this.description,
    fileContent = this.fileContent,
    userId = userId,
    startDate = this.startDate,
    endDate = this.endDate,
)