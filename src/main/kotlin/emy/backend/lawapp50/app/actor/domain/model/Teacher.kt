package emy.backend.lawapp50.app.actor.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.Actor
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.TeacherEntity
import emy.backend.lawapp50.app.school_ecosystem.domain.model.Etablissement
import jakarta.persistence.Column
import jakarta.validation.constraints.Null

data class Teacher(
    val teacherId : Long?,
    val matricule : String? = null,
    val typeTeacherId : Long?,
    val departement : String?,
    val justificatif : String,
    @JsonIgnore
    val faculteId: Long,
    @JsonIgnore
    var userId : Long?,
    var gender : Char? = null,
)

data class TeacherRequest(
    val typeTeacherId : Long?,
    val departement : String?,
    val matricule : String? = null,
    val justificatif : String,
    val faculteId: Long,
    var gender : Char? = null,
    val etablissement : List<EtablissementRequest>
)
fun TeacherRequest.toDomain(userId: Long?) = Teacher(
    teacherId = null,
    matricule = this.matricule,
    typeTeacherId = this.typeTeacherId,
    departement = this.departement,
    justificatif = this.justificatif,
    faculteId = this.faculteId,
    userId = userId,
    gender = this.gender
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


