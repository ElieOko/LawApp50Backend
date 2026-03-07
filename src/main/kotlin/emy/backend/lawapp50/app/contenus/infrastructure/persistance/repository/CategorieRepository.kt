package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CategorieRepository : CoroutineCrudRepository<CategorieEntity, Long>