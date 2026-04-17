package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.*
import emy.backend.lawapp50.app.contenus.domain.model.*
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.user.application.service.*
import emy.backend.lawapp50.route.contenu.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.*
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.Profile
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/{version}/")
@Profile("dev")
class LikeContenusController (
    private val s: LikeContenusService,
    private  val sentry: SentryService,
    private val userS: UserService,
) {
    @Operation(summary = "Creation de like")
    @PostMapping(LikeScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createLike(
        @Valid @RequestBody rData: FavorisContenuRequest, req: HttpServletRequest, @PathVariable version: String
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
            val existingFavorite = s.getFavoriteIfExist(rData.contenuId, user.userId!!)
            val createLike = if (existingFavorite != null) {
                existingFavorite.like = !existingFavorite.like
                existingFavorite.isActive = !existingFavorite.isActive
                s.create(existingFavorite)
            } else s.create(data)
            userS
            mapOf("likes" to createLike)
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

    @GetMapping(LikeScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllFavoris(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("likes" to s.getAll())
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

    @GetMapping("${LikeScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("likes" to s.findById(id))
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