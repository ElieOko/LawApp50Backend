package emy.backend.lawapp50.app.school_ecosystem.domain.request

import emy.backend.lawapp50.app.school_ecosystem.domain.model.*

data class TravailPratiqueRequest(
    val tp : TPRequest,
    val questionOption : Map<QuestionRequest,List<QuestionOptionRequest>>?,
    val questionOuverte : Map<QuestionRequest,List<QuestionOuverteRequest>>?,
    val questionCaseStudy : Map<QuestionRequest,List<QuestionCaseStudyRequest>>?
)
