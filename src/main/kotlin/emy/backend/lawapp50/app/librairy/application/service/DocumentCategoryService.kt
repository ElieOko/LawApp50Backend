package emy.backend.lawapp50.app.librairy.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentCategoryEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository.DocumentCategoryRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class DocumentCategoryService(
    private val rep: DocumentCategoryRepository,
) {
    suspend fun create(data: DocumentCategoryEntity): DocumentCategoryEntity{
        return rep.save(data)
    }

    suspend fun findById(id:Long): DocumentCategoryEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<DocumentCategoryEntity>{
        return rep.findAll().map{it}.toList()
    }

    suspend fun getCateDocIfExist(documentId: Long, categoryId: Long): DocumentCategoryEntity? {
        val cat:DocumentCategoryEntity ? = rep.findCateDocExist(documentId, categoryId).firstOrNull()
        return cat
    }
}