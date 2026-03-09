package emy.backend.lawapp50.app.actor.application.service

import emy.backend.lawapp50.app.actor.domain.model.*
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.actor.infrastructure.persistance.repository.StudentRepository
import emy.backend.lawapp50.app.school_ecosystem.application.service.PromotionService
import emy.backend.lawapp50.app.school_ecosystem.domain.model.Etablissement
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.EtablissementRepository
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.FaculteRepository
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.PromotionRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class StudentService(
    private val repository: StudentRepository,
    private val promotion : PromotionRepository,
    private val faculte : FaculteRepository,
    private val etablissement: EtablissementRepository
) {
    suspend fun finAllStudent() = coroutineScope {
        repository.findAll().map { it.toDomain() }
    }
    suspend fun create(model : Student) = coroutineScope{
        repository.save(model.toEntity()).toDomain()
    }
    suspend fun findByIdStudent(id : Long): Student? = coroutineScope  {
        repository.findById(id)?.toDomain() ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID $id not found.")
    }
    suspend fun findByIdUser(userId : Long)  = coroutineScope {
        val data = repository.findByUser(userId)?.toDomain()
        val promo = promotion.findById(data?.promotionId!!)?.toDomain()
        val ets = etablissement.findById(data.etablissementId!!)?.toDomain()
        mapOf("student" to data, "promotion" to promo, "etablissement" to ets)
    }
    suspend fun update(id : Long,model: Student): Student {
        val data = repository.findById(id)
        if (data != null && model.userId == data.userId) {
            return repository.save(model.toEntity()).toDomain()
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials.")
    }
}