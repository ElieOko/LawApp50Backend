package emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.repository

import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity.OuvrageEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface OuvrageRepository : CoroutineCrudRepository<OuvrageEntity, Long>