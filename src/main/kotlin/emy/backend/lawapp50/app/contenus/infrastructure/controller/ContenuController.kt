package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.*
import emy.backend.lawapp50.app.contenus.domain.model.*
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.*
import emy.backend.lawapp50.app.user.application.service.*
import emy.backend.lawapp50.route.contenu.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.*
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import java.time.*

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class ContenuController(
    private val s: ContenuService,
    private val userS: UserService,
    private val conteS: TypeContenuService,
    private val sentry : SentryService,
    private val scop : ScopeContenuRepository,
    private val scopS : ScopeService
) {
    @Operation(summary = "Creation de contenu")
    @PostMapping(ContenuScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createContenu(
        @Valid @RequestBody rData: ContenuRequest,
        req: HttpServletRequest,
        @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
            val typeContenu = conteS.findById(rData.typeContenuId) ?: return@coroutineScope ResponseEntity.badRequest().body(mapOf("errer" to "type contenu avec l'id ${rData.typeContenuId} non trouvé"))
            val data = ContenuEntity(
                userId = user.userId!!,
                title = rData.title,
                description = rData.description,
                fileContent = rData.fileContent,
                isActive = true,
                createdAt = LocalDateTime.now(),
                typeContenuId = rData.typeContenuId
            )

            val createContenu = s.create(data)
            val scope = rData.scope
            val dataScopeContenu = scope?.mapNotNull {
                scopS.findById(it.id)?.id?.let { scopeId ->
                    ScopeContenuEntity(
                        scopeId = scopeId,
                        contenuId = createContenu.id!!,
                        isActive = true
                    )
                }
            }
            val saveScopeContenu = if (!dataScopeContenu.isNullOrEmpty()) {
                dataScopeContenu.map { scop.save(it) }
            } else null

            mapOf("contenu" to s.toDtoEntity( createContenu))
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

    @GetMapping(ContenuScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllContenu(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("contenu" to s.getAll())
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

    @GetMapping("${ContenuScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("contenu" to s.findById(id))
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