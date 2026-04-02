package emy.backend.lawapp50.app.librairy.application.service

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository.DocumentRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class DocumentService(
    private val rep: DocumentRepository,
) {
    suspend fun create(aut: DocumentEntity): DocumentEntity{
        return rep.save(aut)
    }

    suspend fun findById(id:Long): DocumentEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<DocumentEntity>{
        return rep.findAll().map{it}.toList()
    }
}