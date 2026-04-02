package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.AvisDocumentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AvisDocumentRepository : CoroutineCrudRepository<AvisDocumentEntity, Long>
