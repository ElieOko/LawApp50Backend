package emy.backend.lawapp50.app.librairy.infrastructure.controller

import emy.backend.lawapp50.app.librairy.application.service.DocumentService
import emy.backend.lawapp50.app.librairy.domain.DocumentRequest
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.document.DocumentScope
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping
@Profile("dev")
class DocumentController(
    private val service: DocumentService,
    private val userS: UserService,
    private  val sentry: SentryService) {
    @Operation(summary = "Creation de document")
    @PostMapping(DocumentScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun create(
        @Valid @RequestBody rData: DocumentRequest, req: HttpServletRequest, @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
            val data = DocumentEntity(
                userId = user.userId!!,
                title = rData.title,
                description = rData.description,
                typeDocument = rData.typeDocument,
                backGroundImage = rData.backGroundImage,
                fileBook = rData.fileBook,
                isActive = true,
                price = rData.price,
                deviseId = rData.deviseId,
                isPremium = false,
                datePublication = LocalDateTime.now()
            )

            val create = service.create(data)
            mapOf("document" to create)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.document.createdocument.count",
                    distributionName = "api.document.createdocument.latency"
                )
            )
        }
    }

    @GetMapping(DocumentScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAll(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("document" to service.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.document.getalldocument.count",
                    distributionName = "api.document.getalldocument.latency"
                )
            )
        }
    }

    @GetMapping("${DocumentScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("document" to service.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.docment.getonedocument.count",
                    distributionName = "api.document.getonedocument.latency"
                )
            )
        }
    }
}