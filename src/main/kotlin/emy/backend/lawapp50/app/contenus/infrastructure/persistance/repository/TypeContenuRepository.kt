package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.TypeContenuEntity
import org.springframework.data.r2dbc.repository.Query
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface TypeContenuRepository : CoroutineCrudRepository<TypeContenuEntity, Long>
{
    @Query("SELECT * FROM type_contenus WHERE name  = :name")
    fun findTypeContenuExist(@Param("name") name: String): Flow<TypeContenuEntity>

}