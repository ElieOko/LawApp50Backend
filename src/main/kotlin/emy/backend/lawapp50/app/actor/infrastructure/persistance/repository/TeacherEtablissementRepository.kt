package emy.backend.lawapp50.app.actor.infrastructure.persistance.repository

import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.TeacherEtablissementEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TeacherEtablissementRepository  : CoroutineCrudRepository<TeacherEtablissementEntity, Long>{
    @Query("SELECT * FROM teacher_etablissements WHERE teacher_id = :teacherId")
    suspend fun findByTeacher(teacherId: Long) : List<TeacherEtablissementEntity>?
}
