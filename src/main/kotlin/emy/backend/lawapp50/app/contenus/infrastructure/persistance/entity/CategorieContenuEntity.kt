package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name = "categories_contenus")
class CategorieContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("categorie_id")
    var categorieId: Long,
    @Column("contenu_id")
    var contenuId: Long,
    @Column("is_active")
    var isActive: Boolean = true,
)