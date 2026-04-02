package emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDate
import java.time.LocalDateTime

@Table(name = "auteurs")
class AuteurEntity(
    @Id
    @Column( "id")
    val id: Long? = null,
    @Column("name", )
    val name:String,
    @Column("prenom",)
    val prenom:String?,
    @Column("date_naissance")
    val dateNaissance: LocalDate?,
    @Column("nationalite")
    val nationalite:String?,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)
