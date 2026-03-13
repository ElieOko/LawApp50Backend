package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface FavoriContenuRepository : CoroutineCrudRepository<FavorisContenusEntity, Long> {
    @Query("SELECT * FROM favoris_contenus WHERE contenu_id = :contenuId AND user_id = :user")
    fun findFavoriteExist(@Param("contenuId") contenuId: Long, @Param("user") user: Long): Flow<FavorisContenusEntity>?
}

