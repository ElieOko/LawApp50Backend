package emy.backend.lawapp50.app.user.infrastructure.persistance.repository

import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.TypeAccountEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TypeAccountRepository : CoroutineCrudRepository<TypeAccountEntity, Long>