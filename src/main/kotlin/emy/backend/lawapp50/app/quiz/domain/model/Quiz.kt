package emy.backend.lawapp50.app.quiz.domain.model

import java.time.LocalDateTime

data class Quiz(
    val id: Long?,
    val title: String,
    val description: String,
    val userId: Long,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
)

data class QuizDetail(
    val id: Long?,
    val title: String,
    val description: String,
    val userId: Long,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val levels: List<QuizLevelNode>,
)

data class QuizLevelNode(
    val id: Long?,
    val title: String,
    val levelOrder: Int,
    val questions: List<QuizQuestionNode>,
)

data class QuizQuestionNode(
    val id: Long?,
    val title: String,
    val point: Double,
    val options: List<QuizOptionNode>,
)

data class QuizOptionNode(
    val id: Long?,
    val option: String,
    val isValid: Boolean,
)
