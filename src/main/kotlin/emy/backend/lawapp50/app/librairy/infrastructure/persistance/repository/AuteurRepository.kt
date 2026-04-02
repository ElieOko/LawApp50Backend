package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.AuteurEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AuteurRepository : CoroutineCrudRepository<AuteurEntity, Long>