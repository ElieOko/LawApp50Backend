package emy.backend.lawapp50.app.librairy.domain

import java.time.LocalDate
import java.time.LocalDateTime

data class Auteur(
    val id:Long,
    val name:String,
    val prenom:String?,
    val dateNaissance: LocalDate?,
    val nationalite:String?,
    val createdAt: LocalDateTime
)
