package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull

data class Scope(
    val id: Long,
    var name: String,
    var isActive: Boolean = true
)

data class ScopeRequest(
    @NotNull
    val name: String
)