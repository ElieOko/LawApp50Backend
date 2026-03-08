package emy.backend.lawapp50.app.actor.domain.model

import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.Actor
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.TeacherEntity
import emy.backend.lawapp50.app.school_ecosystem.domain.model.Etablissement
import jakarta.persistence.Column
import jakarta.validation.constraints.Null

data class Teacher(
    @Null
    val teacherId : Long?,
    val matricule : String? = null,
    val typeTeacherId : Long?,
    val departement : String?,
    val justificatif : String,
    val faculteId: Long,
    @Null
    var userId : Long?,
    var gender : Char? = null,
)

data class TeacherRequest(
    val typeTeacherId : Long?,
    val departement : String?,
    val justificatif : String,
    val faculteId: Long,
    var gender : Char? = null,
    val etablissment : List<EtablissementRequest>
)

data class EtablissementRequest(val etablisementId : Long)
fun Teacher.toEntity() = TeacherEntity(
    teacherId = this.teacherId,
    matricule = this.matricule,
    typeTeacherId = this.typeTeacherId,
    departement = this.departement,
    justificatif = this.justificatif,
    faculteId = this.faculteId,
    gender = this.gender,
    userId = this.userId
  )


