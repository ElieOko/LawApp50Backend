package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionOptionEntity

class QuestionOption(
    val id: Long? = null,
    val questionId : Long,
    val option : String,
    val isValid: Boolean = false,
)

fun QuestionOption.toEntity() = QuestionOptionEntity(
    id = this.id,
    questionId = this.questionId,
    option = this.option,
    isValid = this.isValid,
)