package emy.backend.lawapp50.app.contenus.application.service

import ScopeRepository
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ScopeService(private  val r:ScopeRepository) {
    suspend fun create(c: ScopeEntity): ScopeEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): ScopeEntity?{
        return r.findById(id)
    }
//    suspend fun getFavoriteIfExist(contenuId: Long, user: Long): ScopeEntity? {
//        val like: ScopeEntity? = r.findFavoriteExist(contenuId, user)?.firstOrNull()
//        return like
//    }
    suspend fun getAll(): List<ScopeEntity>{
        return r.findAll().map{it}.toList()
    }
}