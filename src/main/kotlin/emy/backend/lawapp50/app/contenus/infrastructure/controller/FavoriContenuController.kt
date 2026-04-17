package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.FavoriContenuService
import emy.backend.lawapp50.app.contenus.domain.model.FavorisContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.FavorisContenusEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.FavoriScope
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
@RequestMapping("api/{version}/")
@Profile("dev")
class FavoriContenuController(
    private val s: FavoriContenuService,
    private  val sentry: SentryService,
    private val userS: UserService,
) {
    @Operation(summary = "Creation de favoris")
    @PostMapping(FavoriScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createFavoris(
        @Valid @RequestBody rData: FavorisContenuRequest, req: HttpServletRequest, @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val user = userS.findIdUser(rData.userId) // ?: ResponseEntity.badRequest().body(mapOf("errer" to "User non trouvr"))
            val data = FavorisContenusEntity(
                contenuId = rData.contenuId,
                userId = rData.userId,
                favorite = true,
            )
            val existingFavorite = s.getFavoriteIfExist(rData.contenuId, user.userId!!)
            val createfavoris = if (existingFavorite != null) {
                existingFavorite.favorite = !existingFavorite.favorite
                existingFavorite.isActive = !existingFavorite.isActive
                s.create(existingFavorite)
            } else s.create(data)
            userS
            mapOf("favoris" to createfavoris)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.favoris.createfavoris.count",
                    distributionName = "api.favoris.createfavoris.latency"
                )
            )
        }
    }

    @GetMapping(FavoriScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllFavoris(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("favoris" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.favoris.getallfavoris.count",
                    distributionName = "api.favoris.getallfavoris.latency"
                )
            )
        }
    }

    @GetMapping("${FavoriScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("favoris" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.favoris.getallfavoris.count",
                    distributionName = "api.favoris.getallfavoris.latency"
                )
            )
        }
    }
}