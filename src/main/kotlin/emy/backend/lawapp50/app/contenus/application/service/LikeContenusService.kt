package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.LikeContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.LikeContenusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class LikeContenusService(private val r: LikeContenusRepository) {
    suspend fun create(c: LikeContenusEntity): LikeContenusEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): LikeContenusEntity?{
        return r.findById(id)
    }
    suspend fun getFavoriteIfExist(contenuId: Long, user: Long): LikeContenusEntity? {
        val like: LikeContenusEntity? = r.findFavoriteExist(contenuId, user)?.firstOrNull()
        return like
    }
    suspend fun getAll(): List<LikeContenusEntity>{
        return r.findAll().map{it}.toList()
    }
}