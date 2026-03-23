package emy.backend.lawapp50.app.school_ecosystem.domain.request

import emy.backend.lawapp50.app.school_ecosystem.domain.model.TravailPratique
import org.jetbrains.annotations.NotNull
import java.time.*

data class TPRequest(
    @NotNull
    val title : String,
    val description : String,
    @NotNull
    val matiere : String,
    var fileContent : String? =  null,
    @NotNull
    val promotionId : Long,
    @NotNull
    val etablissementId : Long,
    @NotNull
    val faculteId : Long,
    @NotNull
    val startDate: LocalDate,
    @NotNull
    val endDate: LocalDate,
)
fun TPRequest.toDomain(userId : Long) = TravailPratique(
    title = this.title,
    promotionId = this.promotionId,
    description = this.description,
    matiere = this.matiere,
    fileContent = this.fileContent,
    userId = userId,
    faculteId = this.faculteId,
    etablissementId = this.etablissementId,
    startDate = this.startDate,
    endDate = this.endDate,
)