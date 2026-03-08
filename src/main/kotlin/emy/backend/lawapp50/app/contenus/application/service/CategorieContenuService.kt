package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.CategorieContenuRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class CategorieContenuService(private  val r: CategorieContenuRepository) {
    suspend fun create(c: CategorieContenuEntity): CategorieContenuEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): CategorieContenuEntity?{
        return r.findById(id)
    }

    suspend fun getAll():Flow<CategorieContenuEntity>{
        return r.findAll()
    }
}