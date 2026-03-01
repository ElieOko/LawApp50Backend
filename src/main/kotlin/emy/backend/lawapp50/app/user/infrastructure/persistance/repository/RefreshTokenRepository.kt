package emy.backend.lawapp50.app.user.infrastructure.persistance.repository

import org.springframework.data.repository.kotlin.*
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.*

interface RefreshTokenRepository : CoroutineCrudRepository<RefreshToken, Long> {
    suspend fun findByUserIdAndHashedToken(userId: Long, hashedToken: String): RefreshToken?
    suspend fun deleteByUserIdAndHashedToken(userId: Long, hashedToken: String)
}