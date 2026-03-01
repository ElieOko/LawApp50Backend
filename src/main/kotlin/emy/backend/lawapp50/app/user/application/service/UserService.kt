package emy.backend.lawapp50.app.user.application.service

import emy.backend.lawapp50.app.user.domain.model.User
import emy.backend.lawapp50.app.user.domain.model.UserDto
import emy.backend.lawapp50.app.user.domain.model.request.UserRequestChange
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.UserEntity
import emy.backend.lawapp50.app.user.infrastructure.persistance.mapper.toDomain
import emy.backend.lawapp50.app.user.infrastructure.persistance.repository.UserRepository
import emy.backend.lawapp50.utils.Mode
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.slf4j.LoggerFactory

import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

import emy.backend.lawapp50.security.Auth

import kotlin.time.ExperimentalTime

@Service
@Profile(Mode.DEV)
class UserService(
    private val repository: UserRepository,
//    private val personService: PersonService,
    private val service: TypeAccountService,
    private val auth: Auth
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    val name = "utilisateur"
    @OptIn(ExperimentalTime::class)
    suspend fun createUser(user: User) : UserDto? {
        val entityToSave = UserEntity(
            password = user.password,
            email = user.email,
            phone = user.phone,
            pseudo = user.username,
            city = user.city,
        )
        val savedEntity = repository.save(entityToSave)
        return savedEntity.toDomain()
    }

    suspend fun findAllUser() : Flow<UserDto> {
        val allEntityUser = repository.findAll()
        return allEntityUser.map { it.toDomain() }
    }

    suspend fun findIdUser(id : Long) : UserDto = coroutineScope {
        val userEntity = repository.findById(id)?:throw ResponseStatusException(
            HttpStatusCode.valueOf(404),
            "ID Is Not Found for User with ID $id."
        )
        userEntity.toDomain()
//        }?: throw EntityNotFoundException("Aucun $name avec cet identifiant $id")

    }
    suspend fun findUsernameOrEmail(identifier : String): UserDto? {
        return  repository.findByPhoneOrEmail(identifier)?.toDomain()
    }

    suspend fun findId(id : Long) : UserDto? {
        val userEntity = repository.findById(id)
        return userEntity?.toDomain()
    }


    @OptIn(ExperimentalTime::class)
    suspend fun updateUser(
        id: Long,
        user: UserRequestChange
    ): UserDto ?{
      val userState =  repository.findById(id)
      if (userState?.email == user.email) {
          userState.city = user.city
          val updatedUser = repository.save(userState)
          return updatedUser.toDomain()
      }
      else{
          val state = repository.findByPhoneOrEmail(user.email)
          if(state != null) {
              throw ResponseStatusException(HttpStatus.CONFLICT, "Cette adresse email est déjà utilisé.")
          }
          userState?.email = user.email
          userState?.city = user.city
          val updatedUser = repository.save(userState!!)
          return updatedUser.toDomain()
      }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun updateUsername(
        id: Long,
        username : String
    ): UserDto ?{
        val userState =  repository.findById(id)
        if (userState != null) {
            userState.pseudo = username
            val updatedUser = repository.save(userState)
            return updatedUser.toDomain()
        }
        return null
    }

    suspend fun deleteUser(id : Long) : Boolean{
        if (!repository.existsById(id)){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun $name avec cet identifiant $id")
            //NotFoundException("Aucun $name avec cet identifiant $id")
        }
        repository.deleteById(id)
        return true
    }

    suspend fun findPersonByUser(userId : Long) = coroutineScope{
       // personService.findByIdPersonUser(userId)
    }

    suspend fun isAdmin():Pair<Boolean, Long?>{
        val user = auth.user()
        val state = user?.second?.find { true } == true
        val id = user?.first?.userId ?: throw ResponseStatusException(
            HttpStatusCode.valueOf(404),
            "Authorization denied, please login"
        )
        return state to id
    }
}