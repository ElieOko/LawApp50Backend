package emy.backend.lawapp50.app.evaluation.domain.request

data class TravailPratiqueRequest(
    val tp : TPRequest,
    val questionOption : Map<QuestionRequest,List<QuestionOptionRequest>>?,
    val questionOuverte : Map<QuestionRequest,List<QuestionOuverteRequest>>?,
    val questionCaseStudy : Map<QuestionRequest,List<QuestionCaseStudyRequest>>?
)
