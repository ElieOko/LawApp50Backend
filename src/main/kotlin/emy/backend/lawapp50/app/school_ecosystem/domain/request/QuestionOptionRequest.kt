package emy.backend.lawapp50.app.school_ecosystem.domain.request

import jakarta.validation.constraints.NotNull

class QuestionOptionRequest(
    @NotNull
    val option : String,
)

