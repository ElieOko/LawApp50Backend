package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.*
import emy.backend.lawapp50.app.contenus.domain.model.*
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.user.application.service.*
import emy.backend.lawapp50.route.contenu.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class AvisContenusController(
    private val s: AvisContenusService,
    private val userS: UserService,
    private val sentry : SentryService
) {
    @Operation(summary = "Creation de avis sur contenu")
    @PostMapping(AvisContenuScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createAvisContenu(
        @Valid @RequestBody rData: AvisContenusRequest,
        req: HttpServletRequest,
        @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId)
            val data = AvisContenusEntity(
                contenuId = rData.contenuId,
                cote = rData.cote,
                userId = rData.userId,
                description = rData.description,
                isActive = true
            )

            val createContenu = s.create(data)
            mapOf("avis" to createContenu)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.avis.createcontenu.count",
                    distributionName = "api.avis.createcontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des avis sur contenu")
    @GetMapping(AvisContenuScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllAvisContenu(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("avis" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.avis.getallaviscontenu.count",
                    distributionName = "api.avis.getallaviscontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer un avis sur contenu par id")
    @GetMapping("${AvisContenuScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("avis" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.avis.getallcontenu.count",
                    distributionName = "api.avis.getallcontenu.latency"
                )
            )
        }
    }
}