package emy.backend.lawapp50.app.quiz.infrastructure.persistance.repository

import emy.backend.lawapp50.app.quiz.infrastructure.persistance.entity.QuizEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuizRepository : CoroutineCrudRepository<QuizEntity, Long> {
    fun findAllByIsActiveTrueOrderByCreatedAtDesc(): Flow<QuizEntity>
}
