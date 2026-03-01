package emy.backend.lawapp50.app.actor.infrastructure.persistance.repository

import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.TeacherEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TeacherRepository  : CoroutineCrudRepository<TeacherEntity, Long>{
}
