package emy.backend.lawapp50.app.librairy.domain

class TypeDocument(
    val id : Long? = null,
    val titre : String,
    val isActive: Boolean = true,
)
class TypeDocumentRequest(
    val titre : String
)