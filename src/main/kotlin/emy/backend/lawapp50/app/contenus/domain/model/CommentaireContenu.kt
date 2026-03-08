package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class CommentaireContenu(
    val id: Long? = null,
    var contenuId: Long,
    var userId: Long,
    var description : String,
    var isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class CommentaireContenuRequest(
    @NotNull
    var contenuId: Long,
    @NotNull
    var userId: Long,
    @NotNull
    var description : String,
    )