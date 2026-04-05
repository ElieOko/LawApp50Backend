package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("evaluation_submissions")
class EvaluationSubmissionEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("evaluation_id")
    val evaluationId: Long,
    @Column("user_id")
    val userId: Long,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
