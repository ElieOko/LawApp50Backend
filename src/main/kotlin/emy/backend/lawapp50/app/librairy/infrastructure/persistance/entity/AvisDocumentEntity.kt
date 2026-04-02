package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table("avis_documents")
class AvisDocumentEntity(
    @Id
    @Column("id")
    val id      : Long? = null,
    @Column("user_id")
    val userId  : Long,
    @Column("commentaire")
    val commentaire   : String,
    @Column("cote")
    val cote: Long = 3,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("date_created")
    val dateCreated: LocalDateTime = LocalDateTime.now(),
)