package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull

data class CommentaireResponseContenu(
    val id: Long? = null,
    var commentaireContenuId : Long,
    var userId: Long,
    var description : String,
    var isActive: Boolean = true,
)

data class CommentaireResponseContenuRequest(
    @NotNull
    var commentaireContenuId : Long,
    @NotNull
    var userId: Long,
    @NotNull
    var description : String,
)
