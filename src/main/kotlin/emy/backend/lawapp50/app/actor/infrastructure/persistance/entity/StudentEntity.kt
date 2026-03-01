package emy.backend.lawapp50.app.actor.infrastructure.persistance.entity

import jakarta.persistence.*

@Table(name = "students")
class StudentEntity(
    @Id
    @Column("student_id")
    val studentId : Long? = null,
    @Column("promotion_id")
    val promotionId : Long? = null,
    @Column("etablissement_id")
    val etablissementId : Long? = null,
    @Column("matricule")
    val matricule : String? = null
) : Actor()

