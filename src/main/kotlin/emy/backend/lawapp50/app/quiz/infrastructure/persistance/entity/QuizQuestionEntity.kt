package emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("quiz_questions")
class QuizQuestionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("quiz_level_id")
    val quizLevelId: Long,
    @Column("title")
    val title: String,
    @Column("point")
    val point: Double = 0.0,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
