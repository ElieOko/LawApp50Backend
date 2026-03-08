package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.LikeContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.LikeContenusRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class LikeContenusService(private val r: LikeContenusRepository) {
    suspend fun create(c: LikeContenusEntity): LikeContenusEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): LikeContenusEntity?{
        return r.findById(id)
    }

    suspend fun getAll():Flow<LikeContenusEntity>{
        return r.findAll()
    }
}