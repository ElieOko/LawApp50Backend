package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.LikeContenusEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface LikeContenusRepository : CoroutineCrudRepository<LikeContenusEntity, Long>