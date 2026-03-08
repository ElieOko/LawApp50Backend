package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull

data class LikeContenus(
    val id: Long? = null,
    var contenuId: Long,
    var like: Boolean = true,
    var userId: Long,
    var isActive: Boolean = false
)
data class LikeContenusRequest(
    @NotNull
    var contenuId: Long,
    @NotNull
    var userId: Long
)