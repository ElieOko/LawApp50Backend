package emy.backend.lawapp50.app.contenus.domain.model

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeEntity
import jakarta.validation.constraints.NotNull

data class ScopeContenu(
    val id: Long? = null,
    var scopeId: Long,
    var contenuId: Long,
    var isActive: Boolean = true,
)

data class ScopeContenuRequest(
    @NotNull
    val scopeId: Long,
    @NotNull
    val contenuId:Long
)

data class ScopeContenuDTO(
    val scope: ScopeEntity ?,
    val contenu: Contenu? = null,
    val scopeContenu: ScopeContenuEntity
)