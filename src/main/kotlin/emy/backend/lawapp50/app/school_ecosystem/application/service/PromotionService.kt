package emy.backend.lawapp50.app.school_ecosystem.application.service

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.PromotionRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class PromotionService(
    private val repository: PromotionRepository
) {
    suspend fun findById(id : Long) = coroutineScope {
        val data = repository.findById(id)?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID Is Not Found.")
        data.toDomain()
    }

    suspend fun getAll() = coroutineScope {
        repository.findAll().map { it.toDomain() }
    }
}