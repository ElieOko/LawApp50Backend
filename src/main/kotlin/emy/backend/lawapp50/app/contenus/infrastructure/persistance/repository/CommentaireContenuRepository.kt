package emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository

import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireContenuEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CommentaireContenuRepository : CoroutineCrudRepository<CommentaireContenuEntity, Long>