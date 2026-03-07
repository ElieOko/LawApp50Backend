package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "like_contenus")
class LikeContenusEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("contenu_id")
    var contenuId: Long,
    @Column("is_like")
    var like: Boolean = true,
    @Column("user_id")
    var userId: Long,
    @Column("is_active")
    var isActive: Boolean = false,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)