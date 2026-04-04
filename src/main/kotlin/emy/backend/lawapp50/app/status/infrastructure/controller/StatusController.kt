package emy.backend.lawapp50.app.status.infrastructure.controller

import emy.backend.lawapp50.app.status.application.service.StatusPostService
import emy.backend.lawapp50.app.status.domain.model.StatusPostRequest
import emy.backend.lawapp50.app.status.infrastructure.persistance.entity.StatusEntity
import emy.backend.lawapp50.route.status.StatusScope
import emy.backend.lawapp50.security.Auth
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Tag(name = "Status", description = "Publications de statut (notes / stories)")
@RestController
@RequestMapping
@Profile("dev")
class StatusController(
    private val service: StatusPostService,
    private val auth: Auth,
    private val sentry: SentryService,
) {
    @Operation(summary = "Création d'un statut")
    @PostMapping("/{version}/${StatusScope.PRIVATE}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createStatus(
        @Valid @RequestBody body: StatusPostRequest,
        req: HttpServletRequest,
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val session = auth.user()
            val userId = session?.first?.userId
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), "Accès non autorisé")
            val saved = service.create(
                StatusEntity(
                    userId = userId,
                    backgroundColor = body.backgroundColor,
                    note = body.note,
                    isActive = true,
                    dateCreated = LocalDateTime.now(),
                ),
            )
            ResponseEntity.status(201).body(mapOf("status" to saved))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.status.create.count",
                    distributionName = "api.status.create.latency",
                ),
            )
        }
    }

    @Operation(summary = "Liste des statuts actifs")
    @GetMapping("/{version}/${StatusScope.PROTECTED}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun listActive(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("status" to service.listActive())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.status.list.count",
                    distributionName = "api.status.list.latency",
                ),
            )
        }
    }

    @Operation(summary = "Détail d'un statut par id")
    @GetMapping("/{version}/${StatusScope.PRIVATE}/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("status" to service.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.status.getbyid.count",
                    distributionName = "api.status.getbyid.latency",
                ),
            )
        }
    }
}
