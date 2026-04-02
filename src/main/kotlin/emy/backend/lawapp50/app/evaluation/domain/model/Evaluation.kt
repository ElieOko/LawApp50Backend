package emy.backend.lawapp50.app.evaluation.domain.model

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import java.time.*

class Evaluation(
    val id: Long? = null,
    val title : String,
    val description : String,
    var fileContent : String? =  null,
    val userId : Long,
    val compteur : Long? = 0,
    val startDate: LocalDate,
    val endDate: LocalDate,
)

fun Evaluation.toEntity() = EvaluationEntity(
    id = this.id,
    title = this.title,
    description = this.description,
    fileContent = this.fileContent,
    userId = this.userId,
    compteur = this.compteur,
    startDate = this.startDate,
    endDate = this.endDate
)