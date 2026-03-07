package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FavoriContenuRepository : CoroutineCrudRepository<FavorisContenusEntity, Long>