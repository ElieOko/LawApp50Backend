package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionRepository : CoroutineCrudRepository<QuestionEntity, Long> {
    fun findAllByEvaluationIdAndIsActiveTrueOrderByIdAsc(evaluationId: Long): Flow<QuestionEntity>
    fun findByEvaluationIdIn(evaluationIds: List<Long>): Flow<QuestionEntity>
}
