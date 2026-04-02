package emy.backend.lawapp50.app.evaluation.domain.request

import jakarta.validation.constraints.NotNull

class QuestionOptionRequest(
    @NotNull
    var option : String,
    val isValid : Boolean = false,
)

