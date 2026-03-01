package emy.backend.lawapp50.app.actor.infrastructure.persistance.entity

import jakarta.persistence.Column
import java.time.LocalDateTime

abstract class Actor(
    @Column("user_id")
    val userId : Long? = null,
    @Column("first_name")
    val firstName : String = "",
    @Column("last_name")
    val lastName : String = "",
    @Column("full_name")
    val fullName : String = "",
    @Column("gender")
    val gender : Char? = null,
)