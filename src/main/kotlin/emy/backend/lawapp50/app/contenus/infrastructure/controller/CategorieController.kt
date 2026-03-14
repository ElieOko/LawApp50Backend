package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.CategorieService
import emy.backend.lawapp50.app.contenus.domain.model.CategorieRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieEntity
import emy.backend.lawapp50.route.contenu.CategorieScope
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
class CategorieController (
    private val s: CategorieService,
    private val sentry : SentryService
) {
    @Operation(summary = "Creation de categorie")
    @PostMapping("/{version}/${CategorieScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createCategorie(
        @Valid @RequestBody rData: CategorieRequest, req: HttpServletRequest
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = CategorieEntity(
                name = rData.name,
                isActive = true
            )

            val createCategorie = s.create(data)
            mapOf("categorie" to createCategorie)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.createcategorie.count",
                    distributionName = "api.categorie.createcategorie.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des categories contenu")
    @GetMapping("/{version}/${CategorieScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllCategorie(req: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("categorie" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.getallcategorie.count",
                    distributionName = "api.categorie.getallcategorie.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer une categorie contenu par id")
    @GetMapping("/{version}/${CategorieScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("categorie" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.getaonecategorie.count",
                    distributionName = "api.categorie.getaonecategorie.latency"
                )
            )
        }
    }
}
