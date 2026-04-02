package emy.backend.lawapp50.app.evaluation.domain.request

import jakarta.validation.constraints.NotNull

class QuestionOuverteRequest(
    @NotNull
    var title : String,
    var fileContent : String? =  null,
)

