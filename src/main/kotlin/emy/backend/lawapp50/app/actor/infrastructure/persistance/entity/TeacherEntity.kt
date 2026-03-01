package emy.backend.lawapp50.app.actor.infrastructure.persistance.entity

import jakarta.persistence.*

@Table(name="teachers")
class TeacherEntity (
    @Id
    @Column(name="teacher_id")
    val teacherId : Long?,
    @Column(name="matricule")
    val matricule : String?,
    @Column(name = "type_teacher_id")
    val typeTeacherId : Long?,
    @Column(name= "departement")
    val departement : String?,
    @Column(name="justificatif")
    val justificatif : String,
    @Column(name="faculte_id")
    val  faculteId: String,
) : Actor()