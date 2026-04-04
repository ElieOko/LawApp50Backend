package emy.backend.lawapp50.app.status.domain.model

import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

data class StatusPost(
    val id: Long?,
    val userId: Long,
    val backgroundColor: String,
    val note: String,
    val isActive: Boolean,
    val dateCreated: LocalDateTime,
)

data class StatusPostRequest(
    @field:NotBlank
    val note: String,
    val backgroundColor: String = "",
)
