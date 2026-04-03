package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentCategoryEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.TypeDocumentEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface TypeDocumentRepository : CoroutineCrudRepository<TypeDocumentEntity, Long>
{
    @Query("SELECT * FROM document_categories WHERE titre  = :titre")
    fun findTypeDocExist(@Param("titre") titre: String): Flow<TypeDocumentEntity>
}