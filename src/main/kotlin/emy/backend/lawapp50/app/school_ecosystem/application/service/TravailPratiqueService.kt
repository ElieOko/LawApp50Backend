package emy.backend.lawapp50.app.school_ecosystem.application.service

import emy.backend.lawapp50.app.school_ecosystem.domain.model.*
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.toDomain
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.*
import emy.backend.lawapp50.app.user.domain.model.Account
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.toDomain
import kotlinx.coroutines.*
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.*
import org.springframework.web.server.ResponseStatusException

@Service
class TravailPratiqueService (
    private val repository : TravailPratiqueRepository
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