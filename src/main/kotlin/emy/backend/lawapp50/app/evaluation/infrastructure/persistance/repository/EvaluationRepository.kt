package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EvaluationRepository  : CoroutineCrudRepository<EvaluationEntity, Long>{

    @Query("SELECT * FROM evaluations WHERE user_id = :userId")
    suspend fun getAllByUser(userId: Long) : Flow<EvaluationEntity?>

    @Modifying
    @Query("""UPDATE evaluations SET is_active = :state WHERE user_id = :userId""")
    suspend fun setUpdateIsAvailable(userId: Long, state: Boolean = false): Int
}
