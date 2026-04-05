package emy.backend.lawapp50.app.evaluation.domain.model

import java.time.LocalDate

data class EvaluationAnswerableSummary(
    val id: Long,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

data class EvaluationPassageView(
    val id: Long,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val questions: List<EvaluationQuestionPassageView>,
)

data class EvaluationQuestionPassageView(
    val id: Long,
    val title: String,
    val point: Double,
    val kind: String,
    val options: List<EvaluationOptionPassageView>?,
    val promptTitle: String?,
    val promptFileContent: String?,
)

data class EvaluationOptionPassageView(
    val id: Long,
    val option: String,
)

data class EvaluationSubmitResult(
    val submissionId: Long,
    val scoreQcm: Double,
    val maxQcmPoints: Double,
    val message: String,
)
