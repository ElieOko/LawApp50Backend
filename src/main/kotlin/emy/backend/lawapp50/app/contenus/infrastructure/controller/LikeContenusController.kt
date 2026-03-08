package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.LikeContenusService
import emy.backend.lawapp50.app.contenus.domain.model.FavorisContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.LikeContenusEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.LikeScope
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
class LikeContenusController (
    private val s: LikeContenusService,
    private  val sentry: SentryService,
    private val userS: UserService,
) {
    @Operation(summary = "Creation de like")
    @PostMapping("/{version}/${LikeScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createLike(
        @Valid @RequestBody rData: FavorisContenuRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
            val data = LikeContenusEntity(
                contenuId = rData.contenuId,
                userId = rData.userId,
                like = true,
                isActive = true
            )

            val createLike = s.create(data)
            mapOf("contenu" to createLike)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.like.createlike.count",
                    distributionName = "api.like.createlike.latency"
                )
            )
        }
    }

    @GetMapping("/{version}/${LikeScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllFavoris(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("accounts" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.like.getalllilke.count",
                    distributionName = "api.like.getalllike.latency"
                )
            )
        }
    }

    @GetMapping("/{version}/${LikeScope.PROTECTED}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
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
                    countName = "api.like.getalllike.count",
                    distributionName = "api.like.getalllike.latency"
                )
            )
        }
    }
}