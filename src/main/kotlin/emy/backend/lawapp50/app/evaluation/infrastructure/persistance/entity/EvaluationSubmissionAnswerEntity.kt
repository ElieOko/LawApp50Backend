package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("evaluation_submission_answers")
class EvaluationSubmissionAnswerEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("submission_id")
    val submissionId: Long,
    @Column("question_id")
    val questionId: Long,
    @Column("selected_option_id")
    val selectedOptionId: Long? = null,
    @Column("text_response")
    val textResponse: String? = null,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
