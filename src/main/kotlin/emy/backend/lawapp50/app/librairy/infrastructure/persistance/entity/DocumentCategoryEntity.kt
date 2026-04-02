package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table("document_categories")
class DocumentCategoryEntity(
    @Id
    @Column("id")
    val id : Long? = null,
    @Column("document_id")
    val documentId : Long,
    @Column("category_id")
    val categoryId : Long,
)