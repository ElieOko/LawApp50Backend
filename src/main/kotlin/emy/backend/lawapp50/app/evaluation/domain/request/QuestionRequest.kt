package emy.backend.lawapp50.app.evaluation.domain.request

import jakarta.validation.constraints.NotNull

class QuestionRequest(
    @NotNull
    var title : String,
    @NotNull
    var point : Double = 0.0,
)

