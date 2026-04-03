package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity

import emy.backend.lawapp50.app.evaluation.domain.model.*
import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.*

@Table(name = "evaluations")
class EvaluationEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("title")
    val title : String,
    @Column("description")
    val description : String,
    @Column("file_content")
    var fileContent : String? =  null,
    @Column("user_id")
    val userId : Long,
    @Column("compteur")
    val compteur : Long? = 0,
    @Column("start_date")
    val startDate: LocalDate,
    @Column("end_date")
    val endDate: LocalDate,
    @Column("is_active")
    val isActive: Boolean = true,
//    @Column("is_closed")
//    val isClosed: Boolean = true,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)

fun EvaluationEntity.toDomain() = Evaluation(
    id = this.id,
    title = this.title,
    description = this.description,
    fileContent = this.fileContent,
    userId = this.userId,
    compteur = this.compteur,
    startDate = this.startDate,
    endDate = this.endDate
)