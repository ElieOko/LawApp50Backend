package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.FavorisDocumentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface FavorisDocumentRepository : CoroutineCrudRepository<FavorisDocumentEntity, Long>
