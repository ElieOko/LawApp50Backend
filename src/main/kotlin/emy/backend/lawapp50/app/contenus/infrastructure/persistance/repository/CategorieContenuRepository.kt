package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieContenuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CategorieContenuRepository : CoroutineCrudRepository<CategorieContenuEntity, Long>