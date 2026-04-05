package emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity

import emy.backend.lawapp50.app.quiz.domain.model.Quiz
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("quizzes")
class QuizEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("title")
    val title: String,
    @Column("description")
    val description: String,
    @Column("user_id")
    val userId: Long,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun QuizEntity.toDomain() = Quiz(
    id = id,
    title = title,
    description = description,
    userId = userId,
    isActive = isActive,
    createdAt = createdAt,
)
