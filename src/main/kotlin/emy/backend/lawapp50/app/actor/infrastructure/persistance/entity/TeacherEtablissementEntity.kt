package emy.backend.lawapp50.app.actor.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*

@Table(name="teacher_etablissements")
class TeacherEtablissementEntity (
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("teacher_id")
    val teacherId: Long,
    @Column("etablissement_id")
    val etablissementId: Long,
)


