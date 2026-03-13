package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireResponseContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.LikeContenusEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.CommentaireResponseContenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class CommentaireResponseContenuService (private val r: CommentaireResponseContenuRepository){
    suspend fun create(c: CommentaireResponseContenuEntity): CommentaireResponseContenuEntity{
        return r.save(c)
    }

    suspend fun findById(id:Long): CommentaireResponseContenuEntity?{
        return r.findById(id)
    }

    suspend fun getAll(): List<CommentaireResponseContenuEntity>{
        return r.findAll().map{it}.toList()
    }
}