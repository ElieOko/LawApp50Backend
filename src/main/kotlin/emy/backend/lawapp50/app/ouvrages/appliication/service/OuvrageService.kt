package emy.backend.lawapp50.app.ouvrages.service

import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity.OuvrageEntity
import emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.repository.OuvrageRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class OuvrageService(
    private val rep: OuvrageRepository
) {
    suspend fun create(aut: OuvrageEntity): OuvrageEntity{
        return rep.save(aut)
    }

    suspend fun findById(id:Long): OuvrageEntity?{
        return rep.findById(id)
    }
    suspend fun getAll(): List<OuvrageEntity>{
        return rep.findAll().map{it}.toList()
    }
}