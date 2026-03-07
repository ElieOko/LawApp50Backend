package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.QuestionOuverteEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface QuestionOuverteRepository  : CoroutineCrudRepository<QuestionOuverteEntity, Long>{

}
