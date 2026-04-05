package emy.backend.lawapp50.app.quiz.infrastructure.persistance.repository

import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.QuizLevelEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuizLevelRepository : CoroutineCrudRepository<QuizLevelEntity, Long> {
    fun findAllByQuizIdAndIsActiveTrueOrderByLevelOrderAsc(quizId: Long): Flow<QuizLevelEntity>
}
