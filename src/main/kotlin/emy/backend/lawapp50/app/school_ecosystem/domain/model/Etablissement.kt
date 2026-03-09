package emy.backend.lawapp50.app.school_ecosystem.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.EtablissementEntity

data class Etablissement(
    val id: Long? = null,
    val name: String,
    @JsonIgnore
    val isActive: Boolean = true
)

fun Etablissement.toEntity() = EtablissementEntity(
    id = this.id,
    name = this.name
)