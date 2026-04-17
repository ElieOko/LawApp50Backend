package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name = "type_contenus")
class TypeContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("name")
    var name: String,
    @JsonIgnore
    @Column("is_active")
    var isActive: Boolean = true,
)