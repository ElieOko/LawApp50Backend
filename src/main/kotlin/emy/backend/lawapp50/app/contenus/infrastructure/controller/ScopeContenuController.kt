package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.*
import emy.backend.lawapp50.route.contenu.*
import emy.backend.lawapp50.security.monitoring.*
import jakarta.servlet.http.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class ScopeContenuController(
    private val s: ScopeContenuService,
    private val sentry : SentryService) {
    @GetMapping(ScopeContenuScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllScopeContenu(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("scopeContenu" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.contenuscope.getallcontenuscope.count",
                    distributionName = "api.contenuscope.getallcontenuscope.latency"
                )
            )
        }
    }

    @GetMapping("${ScopeContenuScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("scopeContenu" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.contenuscope.getallcontenuscope.count",
                    distributionName = "api.contenuscope.getallcontenuscope.latency"
                )
            )
        }
    }
}