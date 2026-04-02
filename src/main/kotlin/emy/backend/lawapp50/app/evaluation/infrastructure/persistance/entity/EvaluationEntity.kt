package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity

import emy.backend.lawapp50.app.evaluation.domain.model.*
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.*

@Table(name = "travail_pratiques")
class TravailPratiqueEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("title")
    val title : String,
    @Column("description")
    val description : String,
    @Column("matiere")
    val matiere : String,
    @Column("file_content")
    var fileContent : String? =  null,
    @Column("promotion_id")
    val promotionId : Long,
    @Column("user_id")
    val userId : Long,
    @Column("etablissement_id")
    val etablissementId : Long,
    @Column("faculte_id")
    val faculteId : Long,
    @Column("start_date")
    val startDate: LocalDate,
    @Column("end_date")
    val endDate: LocalDate,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun TravailPratiqueEntity.toDomain() = TravailPratique(
    id = this.id,
    title = this.title,
    promotionId = this.promotionId,
    description = this.description,
    matiere = this.matiere,
    fileContent = this.fileContent,
    userId = this.userId,
    etablissementId = this.etablissementId,
    faculteId = this.faculteId,
    startDate = this.startDate,
    endDate = this.endDate
)