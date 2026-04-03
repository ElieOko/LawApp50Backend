package emy.backend.lawapp50.app.librairy.domain

data class DocumentCategory(
    val id : Long? = null,
    val documentId : Long,
    val categoryId : Long
)
data class DocumentCategoryRequest(
    val documentId : Long,
    val categoryId : Long
)