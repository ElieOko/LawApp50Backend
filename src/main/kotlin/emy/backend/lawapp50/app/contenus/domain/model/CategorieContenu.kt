package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull
import org.springframework.data.relational.core.mapping.Column

data class CategorieContenu(
    val id: Long? = null,
    var categorieId: Long,
    var contenuId: Long,
    var isActive: Boolean = true
)
data class CategorieContenuRequest(
    @NotNull
    var categorieId: Long,
    @NotNull
    var contenuId: Long,
)