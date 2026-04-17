package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "commentaire_response_contenus")
class CommentaireResponseContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("commentaire_contenu_id")
    var commentaireContenuId : Long,
    @Column("user_id")
    var userId: Long,
    @Column("description")
    var description : String,
    @Column("is_active")
    var isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)