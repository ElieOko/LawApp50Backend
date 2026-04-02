package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(  "category_docs")
class CategoryEntity(
    @Id
    @Column("id")
    val id : Long? = null,
    @Column("domaine_id")
    val domaineId : Long? = null,
    @Column("name")
    val name : String,
    @Column("is_active")
    val isActive: Boolean = true,
)