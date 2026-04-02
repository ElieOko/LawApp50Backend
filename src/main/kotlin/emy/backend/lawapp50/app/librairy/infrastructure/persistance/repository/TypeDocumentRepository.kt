package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.TypeDocumentEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TypeDocumentRepository : CoroutineCrudRepository<TypeDocumentEntity, Long>
