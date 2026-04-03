package emy.backend.lawapp50.app.librairy.domain

import java.time.LocalDateTime

data class AvisDocument(
val id      : Long? = null,
val userId  : Long,
val commentaire   : String,
val cote: Long,
val isActive: Boolean = true,
val dateCreated: LocalDateTime = LocalDateTime.now(),
)


data class AvisDocumentRequest(
    val userId  : Long,
    val commentaire   : String,
    val cote: Long,
  )
