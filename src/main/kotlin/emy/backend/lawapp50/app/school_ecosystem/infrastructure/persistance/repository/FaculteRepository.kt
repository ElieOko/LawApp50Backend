package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.FaculteEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FaculteRepository  : CoroutineCrudRepository<FaculteEntity, Long>{

}
