package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "favoris_contenus")
class FavorisContenusEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("contenu_id")
    var contenuId: Long,
    @Column("user_id")
    var userId: Long,
    @Column("favorite")
    var favorite: Boolean = true,
    @Column("is_active")
    var isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)