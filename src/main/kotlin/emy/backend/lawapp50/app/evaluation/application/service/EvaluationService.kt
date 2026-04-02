package emy.backend.lawapp50.app.evaluation.application.service

import emy.backend.lawapp50.app.evaluation.domain.model.*
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository.*
import kotlinx.coroutines.*
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.*
import org.springframework.web.server.ResponseStatusException

@Service
class TravailPratiqueService (
    private val repository : EvaluationRepository
) {
    suspend fun create(model : TravailPratique) = coroutineScope {
        repository.save(model.toEntity())
    }
    suspend fun findById(id : Long) = coroutineScope {
        val data = repository.findById(id)?: throw ResponseStatusException(HttpStatusCode.valueOf(404), "ID Is Not Found.")
        data.toDomain()
    }
    suspend fun getAllData(){

    }

    suspend fun showDetail(){

    }
}