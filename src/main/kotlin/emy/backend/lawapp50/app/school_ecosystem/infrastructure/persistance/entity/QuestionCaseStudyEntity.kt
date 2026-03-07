package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity

import emy.backend.lawapp50.app.school_ecosystem.domain.model.QuestionCaseStudy
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "question_case_studys")
class QuestionCaseStudyEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("question_id")
    val questionId : Long,
    @Column("title")
    val title : String,
    @Column("file_content")
    var fileContent : String? =  null,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun QuestionCaseStudyEntity.toDomain() = QuestionCaseStudy(
    id = this.id,
    questionId = this.questionId,
    title = this.title,
    fileContent = this.fileContent,
)