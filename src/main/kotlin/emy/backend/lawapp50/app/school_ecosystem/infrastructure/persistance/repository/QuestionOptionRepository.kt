package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionOptionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionOptionRepository  : CoroutineCrudRepository<QuestionOptionEntity, Long>{

}
