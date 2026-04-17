package emy.backend.lawapp50.app.contenus.infrastructure.controller

import emy.backend.lawapp50.app.contenus.application.service.CategorieContenuService
import emy.backend.lawapp50.app.contenus.domain.model.CategorieContenuRequest
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.CategorieContenuEntity
import emy.backend.lawapp50.route.contenu.CategorieContenuScope
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
class CategorieContenuController(
    private val s: CategorieContenuService,
    private val sentry : SentryService
) {
    @Operation(summary = "Creation de categorie contenu")
    @PostMapping(CategorieContenuScope.PRIVATE,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createCategorieContenu(
        @Valid @RequestBody rData: CategorieContenuRequest, req: HttpServletRequest, @PathVariable version: String
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val data = CategorieContenuEntity(
                categorieId = rData.categorieId,
                contenuId = rData.contenuId,
                isActive = true
            )

            val createContenu = s.create(data)
            mapOf("categorieContenu" to createContenu)
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.createcategoriecontenu.count",
                    distributionName = "api.categorie.createcategoriecontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperation des categories contenu")
    @GetMapping(CategorieContenuScope.PROTECTED,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllCategorieContenu(req: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("categorieContenu" to s.getAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.getallcategoriecontenu.count",
                    distributionName = "api.categorie.getallcategoriecontenu.latency"
                )
            )
        }
    }

    @Operation(summary = "recuperer une categorie contenu par id")
    @GetMapping("${CategorieContenuScope.PRIVATE}/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getById(req: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("categorieContenu" to s.findById(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${req.method} /${req.requestURI}",
                    countName = "api.categorie.getaonecategoriecontenu.count",
                    distributionName = "api.categorie.getaonecategoriecontenu.latency"
                )
            )
        }
    }
}