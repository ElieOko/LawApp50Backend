package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationSubmissionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EvaluationSubmissionRepository : CoroutineCrudRepository<EvaluationSubmissionEntity, Long> {
    suspend fun existsByEvaluationIdAndUserId(evaluationId: Long, userId: Long): Boolean
}
