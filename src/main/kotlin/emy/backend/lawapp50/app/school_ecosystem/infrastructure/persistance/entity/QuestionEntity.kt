package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity

import emy.backend.lawapp50.app.school_ecosystem.domain.model.Question
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "questions")
class QuestionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("travail_pratique_id")
    val travailPratiqueId : Long,
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
    travailPratiqueId = this.travailPratiqueId,
    title = this.title,
    point = this.point,
)