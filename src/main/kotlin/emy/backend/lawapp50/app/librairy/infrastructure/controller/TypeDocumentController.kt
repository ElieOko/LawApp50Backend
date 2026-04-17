package emy.backend.lawapp50.app.librairy.infrastructure.controller

import emy.backend.lawapp50.app.librairy.application.service.DocumentService
import emy.backend.lawapp50.app.librairy.application.service.TypeDocumentService
import emy.backend.lawapp50.app.librairy.domain.DocumentRequest
import emy.backend.lawapp50.app.librairy.domain.TypeDocumentRequest
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.DocumentEntity
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.TypeDocumentEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.document.DocumentScope
import emy.backend.lawapp50.route.document.TYPEDocumentScope
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
@RequestMapping("api/{version}/")
@Profile("dev")
class TypeDocumentController(
    private val service: TypeDocumentService,
    private val userS: UserService,
    private  val sentry: SentryService) {
    @Operation(summary = "Creation de document type")
    @PostMapping(TYPEDocumentScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun create(
        @Valid @RequestBody rData: TypeDocumentRequest, req: HttpServletRequest, @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = TypeDocumentEntity(
                titre = rData.titre
            )
            val checkIfExist = service.findIfExist(rData.titre)
            val create = checkIfExist ?: service.create(data)
            mapOf("typeDoc" to create)
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

    @GetMapping(TYPEDocumentScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAll(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("typeDoc" to service.getAll())
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

    @GetMapping("${TYPEDocumentScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("typeDoc" to service.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.docmenttype.getonedocumenttype.count",
                    distributionName = "api.documenttype.getonedocumenttype.latency"
                )
            )
        }
    }
}