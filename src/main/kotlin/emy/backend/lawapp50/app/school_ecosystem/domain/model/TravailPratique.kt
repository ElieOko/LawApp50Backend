package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.TravailPratiqueEntity
import java.time.*

class TravailPratique(
    val id: Long? = null,
    val title : String,
    val description : String,
    val matiere : String,
    var fileContent : String? =  null,
    val promotionId : Long,
    val userId : Long,
    val etablissementId : Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

fun TravailPratique.toEntity() = TravailPratiqueEntity(
    id = this.id,
    title = this.title,
    promotionId = this.promotionId,
    description = this.description,
    matiere = this.matiere,
    fileContent = this.fileContent,
    userId = this.userId,
    etablissementId = this.etablissementId,
    startDate = this.startDate,
    endDate = this.endDate,
)