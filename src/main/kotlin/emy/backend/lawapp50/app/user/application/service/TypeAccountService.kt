package emy.backend.lawapp50.app.user.application.service

import emy.backend.lawapp50.app.user.domain.model.TypeAccount
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.user.infrastructure.persistance.mapper.toDomain
import emy.backend.lawapp50.app.user.infrastructure.persistance.mapper.toEntity
import emy.backend.lawapp50.app.user.infrastructure.persistance.repository.TypeAccountRepository
import emy.backend.lawapp50.utils.Mode
import kotlinx.coroutines.flow.*
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
@Profile(Mode.DEV)
class TypeAccountService(
    private val repository: TypeAccountRepository,
) {
    suspend fun saveAccount(data: TypeAccount): TypeAccount {
        val data = data.toEntity()
        val result = repository.save(data)
        return result.toDomain()
    }
    suspend fun getAll(): Flow<TypeAccount> {
        val data= repository.findAll()
        return data.map {
            TypeAccountEntity(it.id,it.name).toDomain()
        }
    }
    suspend fun findByIdTypeAccount(id : Long) : TypeAccount {
      val data = repository.findById(id) ?: throw ResponseStatusException(
          HttpStatusCode.valueOf(404),
          "ID Is Not Found."
      )
        return data.toDomain()
    }
}