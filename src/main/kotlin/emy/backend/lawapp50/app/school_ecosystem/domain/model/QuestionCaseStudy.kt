package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionCaseStudyEntity
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionEntity
import java.time.LocalDateTime

class QuestionCaseStudy(
    val id: Long? = null,
    val questionId : Long,
    val title : String,
    var fileContent : String? =  null,
)

fun QuestionCaseStudy.toEntity() = QuestionCaseStudyEntity(
    id = this.id,
    questionId = this.questionId,
    title = this.title,
    fileContent = this.fileContent,
)