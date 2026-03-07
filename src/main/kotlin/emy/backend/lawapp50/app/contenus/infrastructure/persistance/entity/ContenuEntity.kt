package emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity

import org.springframework.data.annotation.*
import org.springframework.data.relational.core.mapping.*
import java.time.LocalDateTime

@Table(name = "contenus")
class ContenuEntity(
    @Id
    @Column("id")
    val id: Long? = null,
    @Column("user_id")
    var userId: Long,
    @Column("title")
    var title: String,
    @Column("description")
    var description: String,
    @Column("file_content")
    var fileContent : String,
    @Column("is_active")
    var isActive: Boolean = false,
    @Column("created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)