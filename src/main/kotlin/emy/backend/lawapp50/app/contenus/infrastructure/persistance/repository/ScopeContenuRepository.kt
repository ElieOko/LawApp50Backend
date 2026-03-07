package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ScopeContenuRepository : CoroutineCrudRepository<ScopeContenuEntity, Long>