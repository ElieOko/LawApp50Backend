package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity

import emy.backend.lawapp50.app.school_ecosystem.domain.model.Faculte
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.*

@Table(name = "facultes")
class FaculteEntity (
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("name")
    val name: String,
    @Column("is_active")
    val isActive: Boolean = true
)

fun FaculteEntity.toDomain() = Faculte(
    id = this.id,
    name = this.name
)