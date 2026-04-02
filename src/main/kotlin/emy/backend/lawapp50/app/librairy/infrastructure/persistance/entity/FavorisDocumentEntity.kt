package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table("favorite_documents")
class FavorisDocumentEntity(
    @Id
    @Column("id")
    val id      : Long? = null,
    @Column("user_id")
    val userId  : Long,
    @Column("document_id")
    val documentId  : Long,
    @Column("isLike")
    var isLike: Boolean = true,
    @Column("is_active")
    var isActive: Boolean = true,
    @Column("date_created")
    val dateCreated: LocalDateTime = LocalDateTime.now(),
)