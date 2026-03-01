package emy.backend.lawapp50.app.user.infrastructure.persistance.repository

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.*

interface AccountRepository : CoroutineCrudRepository<AccountEntity, Long>