package emy.backend.lawapp50.app.contenus.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class TypeContenu(
    val id: Long? = null,
    var name: String,
    var isActive: Boolean = true,
)
data class TypeContenuRequest(
    val name: TypesContenu,
    var isActive: Boolean = true
)

enum class TypesContenu {
    TEXTE,
    IMAGE,
    VIDEO,
    AUDIO,
    LIEN,
    STORY,
    LIVE
}
