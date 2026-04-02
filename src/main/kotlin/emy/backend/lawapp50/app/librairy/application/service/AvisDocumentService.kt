package emy.backend.lawapp50.app.librairy.application.service

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.AvisDocumentEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository.AvisDocumentRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class AvisDocumentService(
    private val rep: AvisDocumentRepository,
) {
    suspend fun create(data: AvisDocumentEntity): AvisDocumentEntity{
        return rep.save(data)
    }

    suspend fun findById(id:Long): AvisDocumentEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<AvisDocumentEntity>{
        return rep.findAll().map{it}.toList()
    }
}