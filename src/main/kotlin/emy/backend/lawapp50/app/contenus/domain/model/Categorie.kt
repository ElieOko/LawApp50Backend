package emy.backend.lawapp50.app.contenus.domain.model

import jakarta.validation.constraints.NotNull
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class Categorie(
    val id: Long? = null,
    var name: String,
    var isActive: Boolean = false,
)

data class CategorieRequest(
    @NotNull
    var name: String
)