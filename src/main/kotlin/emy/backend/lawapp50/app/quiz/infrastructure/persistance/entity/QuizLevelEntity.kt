package emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("quiz_levels")
class QuizLevelEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("quiz_id")
    val quizId: Long,
    @Column("title")
    val title: String,
    @Column("level_order")
    val levelOrder: Int = 0,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
