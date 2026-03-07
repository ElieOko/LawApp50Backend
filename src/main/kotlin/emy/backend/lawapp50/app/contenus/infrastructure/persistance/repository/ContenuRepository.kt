package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ContenuRepository : CoroutineCrudRepository<ContenuEntity, Long>