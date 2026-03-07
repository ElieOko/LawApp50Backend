package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name = "scope_contenus")
class ScopeContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("scope_id")
    var scopeId: Long,
    @Column("contenu_id")
    var contenuId: Long,
    @Column("is_active")
    var isActive: Boolean = false,
)