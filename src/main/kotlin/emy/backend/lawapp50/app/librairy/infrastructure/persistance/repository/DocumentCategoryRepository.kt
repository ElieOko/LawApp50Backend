package emy.backend.lawapp50.app.librairy.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentCategoryEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param

interface DocumentCategoryRepository : CoroutineCrudRepository<DocumentCategoryEntity, Long>
{
    @Query("SELECT * FROM document_categories WHERE document_id  = :documentId AND category_id = :categoryId")
    fun findCateDocExist(@Param("documentId") documentId: Long, @Param("categoryId") categoryId: Long): Flow<DocumentCategoryEntity>

}
