package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.TypeContenuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TypeContenuRepository : CoroutineCrudRepository<TypeContenuEntity, Long>