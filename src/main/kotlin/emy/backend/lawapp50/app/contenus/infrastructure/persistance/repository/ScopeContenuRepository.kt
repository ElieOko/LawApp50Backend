package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface ScopeContenuRepository : CoroutineCrudRepository<ScopeContenuEntity, Long>{
    @Query("SELECT * FROM scope_contenus WHERE contenu_id = :contenuId ")
    fun findScopeContenu(@Param("contenuId") contenuId: Long): Flow<ScopeContenuEntity>?
}