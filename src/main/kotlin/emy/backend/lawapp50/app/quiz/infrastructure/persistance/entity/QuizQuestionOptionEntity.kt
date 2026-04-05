package emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("quiz_question_options")
class QuizQuestionOptionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("question_id")
    val questionId: Long,
    @Column("option_text")
    val optionText: String,
    @Column("is_valid")
    val isValid: Boolean = false,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
