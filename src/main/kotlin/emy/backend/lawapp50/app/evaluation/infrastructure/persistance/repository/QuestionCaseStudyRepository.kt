package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.QuestionCaseStudyEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionCaseStudyRepository : CoroutineCrudRepository<QuestionCaseStudyEntity, Long> {
    fun findAllByQuestionIdAndIsActiveTrue(questionId: Long): Flow<QuestionCaseStudyEntity>
}
