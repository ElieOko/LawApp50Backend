package emy.backend.lawapp50.app.school_ecosystem.domain.request

import jakarta.validation.constraints.NotNull

class QuestionRequest(
    @NotNull
    val title : String,
    @NotNull
    val point : Double = 0.0,
)

