package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.AvisContenusEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AvisContenusRepository : CoroutineCrudRepository<AvisContenusEntity, Long>