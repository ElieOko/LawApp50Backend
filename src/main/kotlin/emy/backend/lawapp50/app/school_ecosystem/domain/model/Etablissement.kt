package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.EtablissementEntity

data class Etablissement(
    val id: Long? = null,
    val name: String,
    val isActive: Boolean = true
)

fun Etablissement.toEntity() = EtablissementEntity(
    id = this.id,
    name = this.name
)