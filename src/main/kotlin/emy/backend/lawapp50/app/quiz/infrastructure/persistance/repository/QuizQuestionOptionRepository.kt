package emy.backend.lawapp50.app.quiz.infrastructure.persistance.repository

import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.QuizQuestionOptionEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuizQuestionOptionRepository : CoroutineCrudRepository<QuizQuestionOptionEntity, Long> {
    fun findAllByQuestionIdAndIsActiveTrueOrderByIdAsc(questionId: Long): Flow<QuizQuestionOptionEntity>
}
