package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.AvisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.AvisContenusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class AvisContenusService(private val r: AvisContenusRepository)
{
    suspend fun create(c: AvisContenusEntity): AvisContenusEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): AvisContenusEntity?{
        return r.findById(id)
    }

    suspend fun getAll(): List<AvisContenusEntity>{
        return r.findAll().map{it}.toList()
    }
}
