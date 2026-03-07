package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity

import emy.backend.lawapp50.app.school_ecosystem.domain.model.QuestionOption
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "question_options")
class QuestionOptionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("question_id")
    val questionId : Long,
    @Column("option")
    val option : String,
    @Column("is_valid")
    val isValid: Boolean = false,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun QuestionOptionEntity.toDomain() = QuestionOption(
    id = this.id,
    questionId = this.questionId,
    option = this.option,
    isValid = this.isValid,
)