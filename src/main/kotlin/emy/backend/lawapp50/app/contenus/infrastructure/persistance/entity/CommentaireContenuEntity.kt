package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name = "commentaire_contenus")
class CommentaireContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("contenu_id")
    var contenuId: Long,
    @Column("user_id")
    var userId: Long,
    @Column("description")
    var description : String,
    @Column("is_active")
    var isActive: Boolean = false,
)