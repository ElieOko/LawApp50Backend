package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionOuverteEntity

class QuestionOuverte(
    val id: Long? = null,
    val questionId : Long,
    val title : String,
    var fileContent : String? =  null,
)

fun QuestionOuverte.toEntity() = QuestionOuverteEntity(
    id = this.id,
    questionId = this.questionId,
    title = this.title,
    fileContent = this.fileContent,
)