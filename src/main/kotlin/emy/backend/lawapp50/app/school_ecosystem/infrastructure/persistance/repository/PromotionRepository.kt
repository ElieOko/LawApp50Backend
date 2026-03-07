package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.PromotionEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PromotionRepository  : CoroutineCrudRepository<PromotionEntity, Long>{

}
