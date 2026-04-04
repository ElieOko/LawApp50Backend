package emy.backend.lawapp50.app.status.infrastructure.persistance.repository

import emy.backend.lawapp50.app.status.infrastructure.persistance.entity.StatusEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface StatusRepository : CoroutineCrudRepository<StatusEntity, Long> {
    fun findAllByIsActiveTrueOrderByDateCreatedDesc(): Flow<StatusEntity>
}
