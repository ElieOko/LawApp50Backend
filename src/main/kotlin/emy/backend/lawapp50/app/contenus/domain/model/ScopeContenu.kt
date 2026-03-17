package emy.backend.lawapp50.app.contenus.domain.model

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