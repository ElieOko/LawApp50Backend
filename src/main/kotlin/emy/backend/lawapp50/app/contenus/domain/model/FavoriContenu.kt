package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.time.LocalDateTime

data class FavorisContenu(
    val id: Long? = null,
    var contenuId: Long,
    var userId: Long,
    var favorite: Boolean = true,
    var isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

data class FavorisContenuRequest(
    @NotNull
    var contenuId: Long,
    @NotNull
    var userId: Long
)