package emy.backend.lawapp50.app.librairy.infrastructure.controller

import emy.backend.lawapp50.app.librairy.application.service.AvisDocumentService
import emy.backend.lawapp50.app.librairy.domain.AvisDocumentRequest
import emy.backend.lawapp50.app.librairy.infrastructure.persistance.entity.AvisDocumentEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.document.AVISDocumentScope
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
class AvisDocumentController(
    private val service: AvisDocumentService,
    private val userS: UserService,
    private  val sentry: SentryService) {
    @Operation(summary = "Creation de document avis")
    @PostMapping("/{version}/${AVISDocumentScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createAvisDoc(
        @Valid @RequestBody rData: AvisDocumentRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
            val data = AvisDocumentEntity(
                commentaire = rData.commentaire,
                cote = rData.cote,
                dateCreated = LocalDateTime.now(),
                userId = user.userId!!,
                isActive = true
            )

            val create = service.create(data)
            mapOf("Avisdocument" to create)
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

    @GetMapping("/{version}/${AVISDocumentScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllAvisDoc(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("Avisdocument" to service.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.avisdocument.getallavisdocument.count",
                    distributionName = "api.avisdocument.getallavisdocument.latency"
                )
            )
        }
    }

    @GetMapping("/{version}/${AVISDocumentScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("Avisdocument" to service.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.avisdocment.getoneavisdocument.count",
                    distributionName = "api.avisdocument.getoneavisdocument.latency"
                )
            )
        }
    }
}