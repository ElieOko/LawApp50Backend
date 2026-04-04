package emy.backend.lawapp50.app.status.application.service

import emy.backend.lawapp50.app.status.domain.model.StatusPost
import emy.backend.lawapp50.app.status.infrastructure.persistance.entity.StatusEntity
import emy.backend.lawapp50.app.status.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.status.infrastructure.persistance.repository.StatusRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class StatusPostService(
    private val repository: StatusRepository,
) {
    suspend fun create(entity: StatusEntity): StatusPost {
        return repository.save(entity).toDomain()
    }

    suspend fun findById(id: Long): StatusPost {
        val data = repository.findById(id)
            ?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "Statut introuvable.")
        return data.toDomain()
    }

    suspend fun listActive(): List<StatusPost> {
        return repository.findAllByIsActiveTrueOrderByDateCreatedDesc()
            .map { it.toDomain() }
            .toList()
    }
}
