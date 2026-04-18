package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionCaseStudyEntity
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionOptionEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionCaseStudyRepository : CoroutineCrudRepository<QuestionCaseStudyEntity, Long> {
    fun findAllByQuestionIdAndIsActiveTrue(questionId: Long): Flow<QuestionCaseStudyEntity>
    fun findByQuestionIdIn(questionIds: List<Long>): Flow<QuestionCaseStudyEntity>
}
