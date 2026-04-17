package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.*
import emy.backend.lawapp50.app.contenus.domain.model.*
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.*
import emy.backend.lawapp50.route.contenu.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.*
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class TypeContenuController (
    private val s: TypeContenuService,
    private val sentry: SentryService)
{
    @Operation(summary = "Creation de type de contenu accept : TEXTE,IMAGE,VIDEO,AUDIO,LIEN,STORY,LIVE")
    @PostMapping(TypeContenuScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun create(
        @Valid @RequestBody rData: TypeContenuRequest, req: HttpServletRequest, @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = TypeContenuEntity(
                name = rData.name.toString(),
                isActive = true
            )
            val checkIfExist = s.findIfExist(rData.name.toString())
            val createTypeContenu = checkIfExist ?: s.create(data)
            mapOf("typeContenu" to createTypeContenu)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.typecontenu.createtypecontenu.count",
                    distributionName = "api.typecontenu.createtypecontenu.latency"
                )
            )
        }
    }
    @GetMapping(TypeContenuScope.PROTECTED, produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAll(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("scope" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.typecontenu.getalltypecontenu.count",
                    distributionName = "api.typecontenu.getalltypecontenu.latency"
                )
            )
        }
    }

    @GetMapping("${TypeContenuScope.PRIVATE}/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
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
                    countName = "api.typecontenu.getonetypecontenu.count",
                    distributionName = "api.typecontenu.getonetypecontenu.latency"
                )
            )
        }
    }
}
