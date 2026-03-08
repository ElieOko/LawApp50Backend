package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.CommentaireContenuRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class CommentaireContenuService(private val r: CommentaireContenuRepository) {
    suspend fun create(c: CommentaireContenuEntity): CommentaireContenuEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): CommentaireContenuEntity?{
        return r.findById(id)
    }

    suspend fun getAll():Flow<CommentaireContenuEntity>{
        return r.findAll()
    }
}