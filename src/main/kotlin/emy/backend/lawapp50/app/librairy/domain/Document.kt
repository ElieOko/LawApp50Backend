package emy.backend.lawapp50.app.librairy.domain

import java.time.LocalDateTime

data class Document(
val id      : Long? = null,

val userId  : Long,

val title   : String,

val description   : String,

val typeDocument : Long,

val backGroundImage : String? = null,

val fileBook  : String?,

val isActive: Boolean = true,

val price: Long? = null,

val deviseId: Long? = null,

val isPremium: Boolean = false,

val datePublication: LocalDateTime = LocalDateTime.now(),

)

data class DocumentRequest (
    val userId  : Long,
    val title   : String,
    val description     : String,
    val typeDocument    : Long,
    val backGroundImage : String?,
    val fileBook        : String?,
    val price           : Long?,
    val deviseId        : Long?
)
