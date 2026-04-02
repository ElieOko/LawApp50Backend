package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(  "type_documents")
class TypeDocumentEntity(
    @Id
    @Column("id")
    val id : Long? = null,
    @Column("titre")
    val titre : String,
    @Column("is_active")
    val isActive: Boolean = true,
)