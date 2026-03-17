package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.ScopeContenuRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ScopeContenuService(private val r: ScopeContenuRepository) {
    suspend fun create(c: ScopeContenuEntity): ScopeContenuEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): ScopeContenuEntity?{
        return r.findById(id)
    }
//    suspend fun getFavoriteIfExist(contenuId: Long, user: Long): ScopeContenuEntity? {
//        val like: ScopeContenuEntity? = r.findFavoriteExist(contenuId, user)?.firstOrNull()
//        return like
//    }
    suspend fun getAll(): List<ScopeContenuEntity>{
        return r.findAll().map{it}.toList()
    }
}