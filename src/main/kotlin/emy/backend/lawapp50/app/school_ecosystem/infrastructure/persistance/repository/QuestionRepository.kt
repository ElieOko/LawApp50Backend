package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionRepository  : CoroutineCrudRepository<QuestionEntity, Long>{

}
