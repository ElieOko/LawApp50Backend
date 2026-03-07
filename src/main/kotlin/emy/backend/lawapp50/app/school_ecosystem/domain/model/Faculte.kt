package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.FaculteEntity

data class Faculte (
    val id: Long? = null,
    val name: String,
)

fun Faculte.toEntity() = FaculteEntity(
    id = this.id,
    name = this.name
)