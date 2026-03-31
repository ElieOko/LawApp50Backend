package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.CommentaireContenuService
import emy.backend.lawapp50.app.contenus.application.service.CommentaireResponseContenuService
import emy.backend.lawapp50.app.contenus.domain.model.CommentaireResponseContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireResponseContenuEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.ResponseScope
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class CommentaireResponseContenuController(
    private val s: CommentaireResponseContenuService,
    private val comCont: CommentaireContenuService,
    private val userS: UserService,
    private val sentry : SentryService
) {

    @Operation(summary = "Creation de categorie")
    @PostMapping("/{version}/${ResponseScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createResponseContenuCommentaire(
        @Valid @RequestBody rData: CommentaireResponseContenuRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = CommentaireResponseContenuEntity(
                isActive = true,
                commentaireContenuId = rData.commentaireContenuId,
                userId = rData.userId,
                description = rData.description
            )

            val createComResponse = s.create(data)
            mapOf("responseCom" to createComResponse)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.responsecom.createresponsecom.count",
                    distributionName = "api.responsecom.createresponsecom.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des response commentaires contenu")
    @GetMapping("/{version}/${ResponseScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllResponseContenuCommentaire(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("responseCom" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.responsecom.getallresponsecom.count",
                    distributionName = "api.responsecom.getallresponsecom.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer une Response Contenu Commentaire par id")
    @GetMapping("/{version}/${ResponseScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("responseCom" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.responsecom.getaoneresponsecom.count",
                    distributionName = "api.responsecom.getaoneresponsecom.latency"
                )
            )
        }
    }
}
