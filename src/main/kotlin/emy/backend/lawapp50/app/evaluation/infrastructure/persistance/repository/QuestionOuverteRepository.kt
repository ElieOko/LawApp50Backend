package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionOptionEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionOuverteEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionOuverteRepository : CoroutineCrudRepository<QuestionOuverteEntity, Long> {
    fun findAllByQuestionIdAndIsActiveTrue(questionId: Long): Flow<QuestionOuverteEntity>
    fun findByQuestionIdIn(questionIds: List<Long>): Flow<QuestionOuverteEntity>
}
