package emy.backend.lawapp50.app.ouvrages.infrastructure.persistance.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "auteurs")
data class AuteurEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,
    @Column(name = "name", nullable = false)
    val name:String,
    @Column(name = "prenom", nullable = true)
    val prenom:String?,
    @Column(name = "date_naissance", nullable = true)
    val dateNaissance: LocalDate?,
    @Column(name = "nationalite", nullable = true)
    val nationalite:String?,
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime

)
