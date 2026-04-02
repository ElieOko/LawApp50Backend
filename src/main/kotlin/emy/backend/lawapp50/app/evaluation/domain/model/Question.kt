package emy.backend.lawapp50.app.evaluation.domain.model

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionEntity

class Question(
    val id: Long? = null,
    val evaluationId : Long,
    val title : String,
    val point : Double = 0.0,
)

fun Question.toEntity() = QuestionEntity(
    id = this.id,
    evaluationId = this.evaluationId,
    title = this.title,
    point = this.point,
)