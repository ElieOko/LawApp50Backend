package emy.backend.lawapp50.app.quiz.domain.request

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
data class QuizRequest(
    @field:NotBlank
    val title: String,
    val description: String = "",
    @field:NotEmpty
    @field:Valid
    val levels: List<QuizLevelRequest>,
)

data class QuizLevelRequest(
    @field:NotBlank
    val title: String,
    val levelOrder: Int = 0,
    @field:NotEmpty
    @field:Valid
    val questions: List<QuizQuestionRequest>,
)

data class QuizQuestionRequest(
    @field:NotBlank
    val title: String,
    val point: Double = 1.0,
    @field:NotEmpty
    @field:Valid
    val options: List<QuizOptionRequest>,
)

data class QuizOptionRequest(
    @field:NotBlank
    val option: String,
    val isGoal: Boolean = false,
)
