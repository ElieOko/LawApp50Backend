package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.AvisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.AvisContenusRepository
import kotlinx.coroutines.flow.Flow
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

    suspend fun getAll():Flow<AvisContenusEntity>{
        return r.findAll()
    }}
