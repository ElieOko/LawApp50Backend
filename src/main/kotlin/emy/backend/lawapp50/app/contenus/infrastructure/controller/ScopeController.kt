package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.ScopeService
import emy.backend.lawapp50.route.contenu.ScopeScope
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import jakarta.servlet.http.HttpServletRequest
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class ScopeController(
    private val s: ScopeService,
    private val sentry : SentryService) {
    @GetMapping(ScopeScope.PROTECTED, produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllScope(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("scope" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.scope.getallscope.count",
                    distributionName = "api.scope.getallscope.latency"
                )
            )
        }
    }

    @GetMapping("${ScopeScope.PRIVATE}/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("scope" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.scope.getonescope.count",
                    distributionName = "api.scope.getonescope.latency"
                )
            )
        }
    }
}