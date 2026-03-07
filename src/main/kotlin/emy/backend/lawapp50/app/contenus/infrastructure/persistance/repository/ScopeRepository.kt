import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ScopeRepository : CoroutineCrudRepository<ScopeEntity, Long>