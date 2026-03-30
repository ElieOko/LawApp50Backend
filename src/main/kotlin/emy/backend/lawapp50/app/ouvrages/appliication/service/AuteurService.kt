package emy.backend.lawapp50.app.ouvrages.service

import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity.AuteurEntity
import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.repository.AuteurRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class AuteurService( private val rep : AuteurRepository) {
    suspend fun create(aut: AuteurEntity): AuteurEntity{
        return rep.save(aut)
    }

    suspend fun findById(id:Long): AuteurEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<AuteurEntity>{
        return rep.findAll().map{it}.toList()
    }
}