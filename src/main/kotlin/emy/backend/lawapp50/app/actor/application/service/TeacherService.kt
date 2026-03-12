package emy.backend.lawapp50.app.actor.application.service

import emy.backend.lawapp50.app.actor.domain.model.*
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.actor.infrastructure.persistance.repository.TeacherEtablissementRepository
import emy.backend.lawapp50.app.actor.infrastructure.persistance.repository.TeacherRepository
import emy.backend.lawapp50.app.school_ecosystem.domain.model.Etablissement
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.EtablissementRepository
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.FaculteRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TeacherService(
    val repository: TeacherRepository,
    private val faculte : FaculteRepository,
    private val etablissement: EtablissementRepository,
    private val teacherEtablissementRepository: TeacherEtablissementRepository
) {
    suspend fun finAll() = coroutineScope {
        repository.findAll().map { it.toDomain() }
    }
    suspend fun create(model : Teacher) = coroutineScope{
        repository.save(model.toEntity()).toDomain()
    }
    suspend fun findByIdTeacher(id : Long): Teacher? = coroutineScope  {
        repository.findById(id)?.toDomain() ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID $id not found.")
    }
    suspend fun findByIdUser(userId : Long) = coroutineScope {
        val listEtablissement = mutableListOf<Etablissement?>()
        val teacher = repository.findByUser(userId)?.toDomain()
        val fac = faculte.findById(teacher?.faculteId!!)
        val itemsEtablissement = teacherEtablissementRepository.findByTeacher(teacher?.teacherId!!)
        itemsEtablissement?.forEach {
            listEtablissement.add(etablissement.findById(it.etablissementId)?.toDomain())
        }
        mapOf("teacher" to teacher, "faculte" to fac, "etablissement" to listEtablissement)
    }
    suspend fun update(id : Long,model: Teacher): Teacher {
        val data = repository.findById(id)
        if (data != null && model.userId == data.userId) {
            return repository.save(model.toEntity()).toDomain()
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials.")
    }
    suspend fun checkUser(userId: Long) = coroutineScope{
        if (repository.findByUser(userId) != null) throw ResponseStatusException(HttpStatus.BAD_REQUEST,"Ce compte est déjà utilisé par un enseignant. Si vous pensez qu’il s’agit d’une erreur, veuillez contacter l’administration." )
        false
    }
}