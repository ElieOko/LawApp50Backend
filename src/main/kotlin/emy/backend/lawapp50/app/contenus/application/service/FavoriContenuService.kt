package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.FavoriContenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.stereotype.Service

@Service
class FavoriContenuService(private val r: FavoriContenuRepository) {
    suspend fun create(c: FavorisContenusEntity): FavorisContenusEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): FavorisContenusEntity?{
        return r.findById(id)
    }

    suspend fun getFavoriteIfExist(contenuId: Long, user: Long): FavorisContenusEntity? {
        val fav: FavorisContenusEntity? = r.findFavoriteExist(contenuId, user)?.firstOrNull()
        return fav
    }

    suspend fun getAll():Flow<FavorisContenusEntity>{
        return r.findAll()
    }
}