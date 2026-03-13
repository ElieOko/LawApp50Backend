package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.ContenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ContenuService(
    private val r: ContenuRepository
) {
    suspend fun create(c: ContenuEntity): ContenuEntity{
       return r.save(c)
    }

    suspend fun findById(id:Long): ContenuEntity?{
        return r.findById(id)
    }

    suspend fun getAll(): List<ContenuEntity>{
        return r.findAll().map{it}.toList()
    }
}