package emy.backend.lawapp50.app.status.infrastructure.persistance.entity

import emy.backend.lawapp50.app.status.domain.model.StatusPost
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("status_posts")
class StatusEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("user_id")
    val userId: Long,
    @Column("background_color")
    val backgroundColor: String = "",
    @Column("note")
    val note: String,
    @Column("is_active")
    val isActive: Boolean = true,
    @Column("date_created")
    val dateCreated: LocalDateTime = LocalDateTime.now(),
)

fun StatusEntity.toDomain() = StatusPost(
    id = id,
    userId = userId,
    backgroundColor = backgroundColor,
    note = note,
    isActive = isActive,
    dateCreated = dateCreated,
)
