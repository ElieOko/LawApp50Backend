package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.CategorieRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class CategorieService(private val r: CategorieRepository) {
    suspend fun create(c: CategorieEntity): CategorieEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): CategorieEntity?{
        return r.findById(id)
    }

    suspend fun getAll():Flow<CategorieEntity>{
        return r.findAll()
    }
}