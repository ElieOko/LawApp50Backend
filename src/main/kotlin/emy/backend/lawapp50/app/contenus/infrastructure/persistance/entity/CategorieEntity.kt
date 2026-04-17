package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name = "categories")
class CategorieEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("name")
    var name: String,
    @Column("is_active")
    var isActive: Boolean = true,
)