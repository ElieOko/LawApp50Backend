package emy.backend.lawapp50.app.contenus.domain.model

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.TypeContenuEntity
import emy.backend.lawapp50.app.user.domain.model.UserDto
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class Contenu(
    val id: Long? = null,
    val userId: Long,
    val typeContenuId: Long?,
    var title: String,
    var description: String,
    var fileContent : String,
    var isActive: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

class ContenuRequest(
    @NotNull
    val userId: Long,
    @NotNull
    val typeContenuId: Long,
    @NotNull
    val title: String,
    @NotNull
    val description: String,
    @NotNull
    val fileContent: String,
    val scope: List<Scope>?
)

class ContenuDto(
    val contenu: ContenuEntity,
    val typeContenu: TypeContenuEntity?,
    val scope : List<ScopeEntity?>?,
    val user: UserDto?
)