package emy.backend.lawapp50.app.user.application.service

import emy.backend.lawapp50.app.actor.application.service.StudentService
import emy.backend.lawapp50.app.actor.application.service.TeacherService
import emy.backend.lawapp50.app.evaluation.application.service.EvaluationService
import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository.EvaluationRepository
import emy.backend.lawapp50.app.user.domain.model.*
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.user.domain.model.request.AccountRequest
import emy.backend.lawapp50.app.user.domain.model.request.VerifyRequest
import emy.backend.lawapp50.app.user.infrastructure.persistance.mapper.toDomain
import emy.backend.lawapp50.app.user.infrastructure.persistance.repository.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import org.springframework.transaction.annotation.Transactional
//import server.web.casa.adaptater.provide.twilio.TwilioService
import java.security.MessageDigest
import java.time.*
import java.util.*
import kotlin.time.ExperimentalTime
import emy.backend.lawapp50.utils.Mode
import emy.backend.lawapp50.app.user.infrastructure.persistance.repository.RefreshTokenRepository
import emy.backend.lawapp50.security.*
import emy.backend.lawapp50.utils.isEmailValid
import emy.backend.lawapp50.utils.normalizeAndValidatePhoneNumberUniversal
import server.web.casa.adaptater.provide.twilio.TwilioService

@Service
@Profile(Mode.DEV)
class AuthService(
    private val jwtService: JwtService,
    private val userRepository: UserRepository,
    private val hashEncoder: HashEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val serviceMultiAccount: AccountUserService,
    private val typeAccountService: TypeAccountService,
    private val accountService: AccountService,
    private val student : StudentService,
    private val teacher : TeacherService,
    private val evaluation : EvaluationRepository
//    private val twilio : TwilioService,
    ) {
    private val log = LoggerFactory.getLogger(this::class.java)
    data class TokenPair(
        val accessToken: String,
        val refreshToken: String
    )
    @OptIn(ExperimentalTime::class)
    suspend fun register(user: User): UserDto {
        var phone = ""
        var state = false
        if (user.phone != null) {
            if (user.phone.isNotEmpty()){
                phone =  normalizeAndValidatePhoneNumberUniversal(user.phone) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce numero n'est pas valide.")
                if(userRepository.findByPhoneOrEmail(phone) != null) throw ResponseStatusException(HttpStatus.CONFLICT, "Ce numéro de téléphone est déjà associé à un compte existant.")
                state = true
            }
        }
        if (user.email != null){
            if(user.email.isNotEmpty()){
                if(userRepository.findByPhoneOrEmail(user.email) != null) throw ResponseStatusException(HttpStatus.CONFLICT, "Cette adresse mail est déjà associé à un compte existant.")
                state = true
            }
        }
        if (!state) throw ResponseStatusException(HttpStatus.CONFLICT, "Vous devez renseigner l'email")
        val entity = UserEntity(
            password = hashEncoder.encode(user.password),
            email = user.email,
            pseudo = user.username,
            phone = phone,
            city = user.city,
            lastName = user.lastName,
            firstName = user.firstName
        )
        log.info("Creating user ${user.userId}")
        val savedEntity = userRepository.save(entity)
//        if (accountItems?.isNotEmpty() == true){
//            serviceMultiAccount.save(AccountUser(userId = savedEntity.userId!!, accountId =accountItems[0].typeAccount))
//        }
//        val newAccessToken = jwtService.generateAccessToken(savedEntity.userId!!.toHexString())
        val userData : UserDto = savedEntity.toDomain()
        return userData
    }
    suspend fun login(identifier: String, password: String): Pair<TokenPair, UserFullDTO>  =
        coroutineScope {
            var validIdentifier = identifier
            if (isEmailValid(identifier)) validIdentifier = identifier
            val user = userRepository.findByPhoneOrEmail(validIdentifier) ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials.")
            if (!user.isValid) throw ResponseStatusException(HttpStatusCode.valueOf(403), "Ce compte n'est pas vérifier.")
            if(!hashEncoder.matches(password, user.password.toString())) throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials.")
            log.info("Logging into user ${user.userId}")
            val newAccessToken = jwtService.generateAccessToken(user.userId!!.toHexString())
            val newRefreshToken = jwtService.generateRefreshToken(user.userId!!.toHexString())
            val accounts = if (serviceMultiAccount.count() != 0L) serviceMultiAccount.getAll().filter { it.userId == user.userId }.toList() else emptyList()
            val accountMultiple: List<AccountDTO> = if (accounts.isNotEmpty()) accounts.map {
                val data = accountService.findByIdAccount(it.accountId)
                AccountDTO(id = data.id, name = data.name, typeAccount = typeAccountService.findByIdTypeAccount(data.typeAccountId))
            }.toList() else emptyList()
            var profile : Any? = null
            if (accountMultiple.isNotEmpty()){
                profile = when(accountMultiple[0].id?.toInt()){
                    2 -> mapOf("type" to  2, "students" to student.findByIdUser(user.userId))
                    3 -> mapOf("type" to  3, "teacher" to teacher.findByIdUser(user.userId))
                    else -> null
                }
            }
            storeRefreshToken(user.userId,newRefreshToken)
            val result = Pair(
                TokenPair(accessToken = newAccessToken, refreshToken = newRefreshToken),
                UserFullDTO(user.toDomain(),profile)
            )
            result
     }
//    suspend fun generateOTP(identifier: String): Triple<String?, String, String> {
//       var validIdentifier = normalizeAndValidatePhoneNumberUniversal(identifier)
//       if (isEmailValid(identifier)) validIdentifier = identifier
//       userRepository.findByPhoneOrEmail(validIdentifier.toString()) ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Idenfiant invalide.")
//       val status = if (isEmailValid(identifier)) twilio.generateVerifyOTP(identifier, channel = "email") else twilio.generateVerifyOTP(identifier)
//       return Triple(status, if (status == "pending") "Votre code de vérification a été envoyé avec suucès" else "Erreur identifiant non prises en charge",identifier)
//    }
//    suspend fun verifyOTP(user : VerifyRequest): Pair<Long, String?> {
//       val userSecurity = userRepository.findByPhoneOrEmail(user.identifier) ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Idenfiant invalide.")
//       return Pair(userSecurity.userId!!,twilio.checkVerify(code = user.code, contact = user.identifier))
//    }
    suspend fun changePassword(id : Long,new : String): UserDto {
        val data = userRepository.findById(id)
        if (data != null) {
            data.password = hashEncoder.encode(new)
            val updatedUser = userRepository.save(data)
            return updatedUser.toDomain()
        }
        throw ResponseStatusException(HttpStatusCode.valueOf(403), "ID invalide.")
    }
    suspend fun goCertification(id : Long,state : Boolean) = coroutineScope {
        val user = userRepository.findById(id)?:throw ResponseStatusException(
            HttpStatusCode.valueOf(404),
            "ID Is Not Found for User with ID $id."
        )
        user.isCertified = state
        userRepository.save(user)
    }

    suspend fun lockedOrUnlocked(userId: Long, isLock: Boolean = true) : Boolean = coroutineScope{
    val state = when {
            userRepository.findById(userId) != null -> {
                //evaluation
                if (evaluation.getAllByUser(userId).toList().isNotEmpty()) evaluation.setUpdateIsAvailable(userId, !isLock)
                userRepository.isLock(userId, isLock)
                true
            }
            else-> false
        }
        state
    }

    @Transactional
    suspend fun refresh(refreshToken: String): TokenPair {
        if(!jwtService.validateRefreshToken(refreshToken)) throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid refresh token.")
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findById(userId.toLong()) ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid refresh token.")
        val hashed = hashToken(refreshToken)
        refreshTokenRepository.findByUserIdAndHashedToken(user.userId!!, hashed) ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Refresh token not recognized (maybe used or expired?)")
        refreshTokenRepository.deleteByUserIdAndHashedToken(user.userId, hashed)
        val newAccessToken = jwtService.generateAccessToken(userId)
        val newRefreshToken = jwtService.generateRefreshToken(userId)
        storeRefreshToken(user.userId, newRefreshToken)
        return TokenPair(accessToken = newAccessToken, refreshToken = newRefreshToken)
    }
    @OptIn(ExperimentalTime::class)
    private suspend fun storeRefreshToken(userId: Long, rawRefreshToken: String) {
        val hashed = hashToken(rawRefreshToken)
        val expiryMs = jwtService.refreshTokenValidityMs
        val instant = Instant.now().plusMillis(expiryMs)
        val zoneId = ZoneId.systemDefault()
        val expiresAt =  LocalDateTime.ofInstant(instant, zoneId)
        refreshTokenRepository.save(RefreshToken(userId = userId, expiresAt = expiresAt, hashedToken = hashed))
    }
    private fun hashToken(token: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(token.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashBytes)
    }
}