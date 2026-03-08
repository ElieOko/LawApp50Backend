package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.CommentaireContenuService
import emy.backend.lawapp50.app.contenus.domain.model.CommentaireContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CommentaireContenuEntity
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.route.contenu.CommentaireScope
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
class CommentaireContenuController(
    private val s: CommentaireContenuService,
    private val userS: UserService,
    private val sentry : SentryService
) {
    @Operation(summary = "Creation de categorie")
    @PostMapping("/{version}/${CommentaireScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createCommentaire(
        @Valid @RequestBody rData: CommentaireContenuRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = CommentaireContenuEntity(
                isActive = true,
                contenuId = rData.contenuId,
                userId = rData.userId,
                description = rData.description,
            )

            val createCom = s.create(data)
            mapOf("contenu" to createCom)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.commentaire.commentairecreate.count",
                    distributionName = "api.commentaire.commentairecreate.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des commentaire contenu")
    @GetMapping("/{version}/${CommentaireScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllCategorie(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("accounts" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.commentaire.getallcommentaire.count",
                    distributionName = "api.commentaire.getallcommentaire.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer une commentaire contenu par id")
    @GetMapping("/{version}/${CommentaireScope.PROTECTED}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
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
                    countName = "api.categorie.getaonecommentaire.count",
                    distributionName = "api.categorie.getaonecommentaire.latency"
                )
            )
        }
    }
}