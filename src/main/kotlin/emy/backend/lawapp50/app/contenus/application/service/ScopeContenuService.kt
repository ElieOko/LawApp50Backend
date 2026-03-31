package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.domain.model.ScopeContenuDTO
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.ScopeContenuRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ScopeContenuService(
    private val r: ScopeContenuRepository,
    private val scopS: ScopeService
) {
    suspend fun create(c: ScopeContenuEntity): ScopeContenuEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): ScopeContenuEntity?{
        return r.findById(id)
    }
    suspend fun getScopeContenu(contenuId: Long): List<ScopeContenuDTO>? {
        val scop = r.findScopeContenu(contenuId)?.map { toDtoEntity(it) }?.toList()
        return scop
    }
    suspend fun getAll(): List<ScopeContenuEntity>{
        return r.findAll().map{it}.toList()
    }

    suspend fun toDtoEntity(it: ScopeContenuEntity): ScopeContenuDTO {
        return ScopeContenuDTO(
            scope = scopS.findById(it.scopeId),
            contenu = null,
            scopeContenu = it
        )
    }
}