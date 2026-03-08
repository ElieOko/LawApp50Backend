package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.AvisContenusService
import emy.backend.lawapp50.app.contenus.domain.model.AvisContenusRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.AvisContenusEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.AvisContenuScope
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
class AvisContenusController(
    private val s: AvisContenusService,
    private val userS: UserService,
    private val sentry : SentryService
) {
    @Operation(summary = "Creation de avis sur contenu")
    @PostMapping("/{version}/${AvisContenuScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createAvisContenu(
        @Valid @RequestBody rData: AvisContenusRequest, req: HttpServletRequest
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
            mapOf("contenu" to createContenu)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.contenu.createcontenu.count",
                    distributionName = "api.contenu.createcontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des avis sur contenu")
    @GetMapping("/{version}/${AvisContenuScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllContenu(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("accounts" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.contenu.getallcontenu.count",
                    distributionName = "api.contenu.getallcontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer un avis sur contenu par id")
    @GetMapping("/{version}/${AvisContenuScope.PROTECTED}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("accounts" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.contenu.getallcontenu.count",
                    distributionName = "api.contenu.getallcontenu.latency"
                )
            )
        }
    }
}