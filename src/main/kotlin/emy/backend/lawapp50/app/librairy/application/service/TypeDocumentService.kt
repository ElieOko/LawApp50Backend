package emy.backend.lawapp50.app.librairy.application.service

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.TypeDocumentEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository.TypeDocumentRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class TypeDocumentService(
    private val rep: TypeDocumentRepository,
) {
    suspend fun create(data : TypeDocumentEntity): TypeDocumentEntity{
        return rep.save(data)
    }

    suspend fun findById(id:Long): TypeDocumentEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<TypeDocumentEntity>{
        return rep.findAll().map{it}.toList()
    }
}