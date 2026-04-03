package emy.backend.lawapp50.app.actor.infrastructure.persistance.entity

import emy.backend.lawapp50.app.actor.domain.model.Teacher

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name="teachers")
class TeacherEntity (
    @Id
    @Column("teacher_id")
    val teacherId: Long?,
    @Column("type_teacher_id")
    val typeTeacherId: Long?,
    @Column("departement")
    val departement: String?,
    @Column("justificatif")
    val justificatif: String,
    @Column("user_id")
    var userId : Long? = null,
    @Column("gender")
    var gender : Char? = null,
    @Column("is_active")
    var isActive : Boolean = false,
)

fun TeacherEntity.toDomain() = Teacher(
    teacherId = this.teacherId,
    typeTeacherId = this.typeTeacherId,
    departement = this.departement,
    justificatif = this.justificatif,
    gender = this.gender,
    userId = this.userId
)
