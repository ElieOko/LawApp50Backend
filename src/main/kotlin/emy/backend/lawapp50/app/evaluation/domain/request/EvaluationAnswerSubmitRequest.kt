package emy.backend.lawapp50.app.evaluation.domain.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class EvaluationAnswerSubmitRequest(
    @field:NotEmpty
    @field:Valid
    val answers: List<QuestionAnswerItemRequest>,
)

data class QuestionAnswerItemRequest(
    @field:NotNull
    val questionId: Long,
    val selectedOptionId: Long? = null,
    val textResponse: String? = null,
)
