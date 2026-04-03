package emy.backend.lawapp50.app.librairy.infrastructure.controller

import emy.backend.lawapp50.app.librairy.application.service.DocumentCategoryService
import emy.backend.lawapp50.app.librairy.domain.DocumentCategoryRequest
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentCategoryEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.document.CATDocumentScope
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Profile("dev")
class DocumentCategorieController(
    private val service: DocumentCategoryService,
    private val userS: UserService,
    private  val sentry: SentryService) {
    @Operation(summary = "Creation de document categorie")
    @PostMapping("/{version}/${CATDocumentScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun create(
        @Valid @RequestBody rData: DocumentCategoryRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val checkIfExist = service.getCateDocIfExist(rData.documentId, rData.categoryId)
            val data = DocumentCategoryEntity(
                documentId = rData.documentId,
                categoryId = rData.categoryId
            )
            val create = checkIfExist ?: service.create(data)
            mapOf("cateDoc" to create)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.documentcat.createdocumentcat.count",
                    distributionName = "api.documentcat.createdocumentcat.latency"
                )
            )
        }
    }

    @GetMapping("/{version}/${CATDocumentScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllFavoris(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("cateDoc" to service.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.documentcat.getalldocumentcat.count",
                    distributionName = "api.documentcat.getalldocumentcat.latency"
                )
            )
        }
    }

    @GetMapping("/{version}/${CATDocumentScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("cateDoc" to service.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.docmentcat.getonedocumentcat.count",
                    distributionName = "api.documentcat.getonedocumentcat.latency"
                )
            )
        }
    }
}