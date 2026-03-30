package emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.repository

import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity.AuteurEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AuteurRepository : CoroutineCrudRepository<AuteurEntity, Long>