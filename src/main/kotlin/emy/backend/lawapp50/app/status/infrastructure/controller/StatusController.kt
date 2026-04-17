package emy.backend.lawapp50.app.status.infrastructure.controller

import emy.backend.lawapp50.app.status.application.service.*
import emy.backend.lawapp50.app.status.domain.model.*
import emy.backend.lawapp50.app.status.infrastructure.persistance.entity.*
import emy.backend.lawapp50.route.status.*
import emy.backend.lawapp50.security.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.tags.*
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.*
import java.time.*

@Tag(name = "Status", description = "Publications de statut (notes / stories)")
@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class StatusController(
    private val service: StatusPostService,
    private val auth: Auth,
    private val sentry: SentryService,
) {
    @Operation(summary = "Création d'un statut")
    @PostMapping(StatusScope.PRIVATE, produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createStatus(
        @Valid @RequestBody body: StatusPostRequest,
        req: HttpServletRequest, @PathVariable version: String,
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
    @GetMapping(StatusScope.PROTECTED, produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun listActive(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
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
    @GetMapping("${StatusScope.PRIVATE}/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
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
