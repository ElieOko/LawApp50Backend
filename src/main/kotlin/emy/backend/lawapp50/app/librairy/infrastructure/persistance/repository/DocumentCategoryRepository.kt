package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentCategoryEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface DocumentCategoryRepository : CoroutineCrudRepository<DocumentCategoryEntity, Long>
