package emy.backend.lawapp50.app.user.infrastructure.persistance.repository

import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.*
import org.springframework.data.r2dbc.repository.*
import org.springframework.data.repository.kotlin.*

interface UserRepository : CoroutineCrudRepository<UserEntity, Long> {

    @Query("SELECT * FROM users WHERE email = :identifier AND is_lock = false")
    suspend fun findByPhoneOrEmail(identifier: String) : UserEntity?

    @Modifying
    @Query("""UPDATE users
    SET is_lock = :lock 
    WHERE id = :userId"""
    )
    suspend fun isLock(userId: Long, lock: Boolean = true): Int
}