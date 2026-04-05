package emy.backend.lawapp50.app.quiz.infrastructure.persistance.repository

import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.QuizQuestionEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuizQuestionRepository : CoroutineCrudRepository<QuizQuestionEntity, Long> {
    fun findAllByQuizLevelIdAndIsActiveTrueOrderByIdAsc(quizLevelId: Long): Flow<QuizQuestionEntity>
}
