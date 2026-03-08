package emy.backend.lawapp50.app.actor.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.StudentEntity
import jakarta.validation.constraints.Null

data class Student(
    var studentId: Long?,
    @JsonIgnore
    var promotionId: Long? = null,
    @JsonIgnore
    var etablissementId: Long? = null,
    val matricule: String? = null,
    @JsonIgnore
    var userId: Long?,
    var gender: Char? = null,
)

data class StudentRequest(
    var promotionId: Long? = null,
    var etablissementId: Long? = null,
    val matricule: String? = null,
    var gender: Char? = null,
)

fun StudentRequest.toDomain(userId: Long?) = Student(
    promotionId = this.promotionId,
    etablissementId = this.etablissementId,
    matricule = this.matricule,
    studentId = null,
    userId = userId,
    gender = this.gender,
)

fun Student.toEntity() = StudentEntity(
    studentId = this.studentId,
    promotionId = this.promotionId,
    etablissementId = this.etablissementId,
    matricule = this.matricule,
    userId = this.userId,
    gender = this.gender
)