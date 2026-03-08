package emy.backend.lawapp50.app.contenus.domain.model

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.AvisContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import emy.backend.lawapp50.app.user.domain.model.UserDto
import jakarta.validation.constraints.NotNull

data class AvisContenus(
    val id: Long? = null,
    var contenuId: Long,
    var userId: Long,
    var description : String?,
    val cote : Long = 0,
    var isActive: Boolean = true,
)

data class AvisContenusRequest(
    @NotNull
    var contenuId: Long,
    @NotNull
    var userId: Long,
    @NotNull
    var description : String?,
    @NotNull
    val cote : Long = 0
)

class AvisContenuDto(
    val avis: AvisContenusEntity,
    val user: UserDto,
    val contenu: ContenuEntity
)