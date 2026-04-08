package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.TypeContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.TypeContenuRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class TypeContenuService(private val r: TypeContenuRepository) {
    suspend fun create(c: TypeContenuEntity): TypeContenuEntity{
        return r.save(c)
    }
    suspend fun findById(id:Long): TypeContenuEntity?{
        return r.findById(id)
    }
    suspend fun findIfExist(name: String):TypeContenuEntity?{
        val type: TypeContenuEntity? = r.findTypeContenuExist(name).firstOrNull()
        return type
    }
    suspend fun getAll(): List<TypeContenuEntity>{
        return r.findAll().map{it}.toList()
    }
}