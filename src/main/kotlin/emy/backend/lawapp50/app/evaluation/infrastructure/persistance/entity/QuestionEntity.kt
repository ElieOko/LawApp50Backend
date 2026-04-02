package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity

import emy.backend.lawapp50.app.evaluation.domain.model.Question
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "evaluation_questions")
class QuestionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("evaluation_id")
    val evaluationId : Long,
    @Column("title")
    val title : String,
    @Column("point")
    val point : Double = 0.0,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun QuestionEntity.toDomain() = Question(
    id = this.id,
    evaluationId = this.evaluationId,
    title = this.title,
    point = this.point,
)