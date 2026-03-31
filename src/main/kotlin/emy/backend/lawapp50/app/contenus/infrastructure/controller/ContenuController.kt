package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.ContenuService
import emy.backend.lawapp50.app.contenus.application.service.ScopeContenuService
import emy.backend.lawapp50.app.contenus.domain.model.ContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ScopeContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.ScopeContenuRepository
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.ContenuScope
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
class ContenuController(
    private val s: ContenuService,
    private val userS: UserService,
    private val sentry : SentryService,
    private val scop : ScopeContenuRepository
) {
    @Operation(summary = "Creation de contenu")
    @PostMapping("/{version}/${ContenuScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createContenu(
        @Valid @RequestBody rData: ContenuRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
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
            val dataScopeContenu = scope?.map{
                ScopeContenuEntity(
                    scopeId = it?.id!!,
                    contenuId = createContenu.id!!,
                    isActive = true
                )
            }?.toList()
            val saveScopeContenu = dataScopeContenu?.map{ scop.save(it) }
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

    @GetMapping("/{version}/${ContenuScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllContenu(req: HttpServletRequest) = coroutineScope {
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

    @GetMapping("/{version}/${ContenuScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
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