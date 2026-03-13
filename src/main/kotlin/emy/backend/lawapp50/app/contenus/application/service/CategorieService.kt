package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.CategorieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CategorieService(private val r: CategorieRepository) {
    suspend fun create(c: CategorieEntity): CategorieEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): CategorieEntity?{
        return r.findById(id)
    }
    suspend fun getAll(): List<CategorieEntity>{
        return r.findAll().map{it}.toList()
    }
}