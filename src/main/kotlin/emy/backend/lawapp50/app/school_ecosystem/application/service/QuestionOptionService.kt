package emy.backend.lawapp50.app.school_ecosystem.application.service

import emy.backend.lawapp50.app.school_ecosystem.domain.model.QuestionOption
import emy.backend.lawapp50.app.school_ecosystem.domain.model.toEntity
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.QuestionOptionRepository
import kotlinx.coroutines.coroutineScope
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class QuestionOptionService(
    private val repository: QuestionOptionRepository
) {
    suspend fun create(model : QuestionOption) = coroutineScope {
        repository.save(model.toEntity())
    }
    suspend fun findById(id : Long) = coroutineScope {
        val data = repository.findById(id)?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID Is Not Found.")
        data.toDomain()
    }
}