package emy.backend.lawapp50.app.actor.infrastructure.controller

import emy.backend.lawapp50.app.actor.application.service.*
import emy.backend.lawapp50.app.actor.domain.model.*
import emy.backend.lawapp50.app.actor.infrastructure.persistance.entity.TeacherEtablissementEntity
import emy.backend.lawapp50.app.actor.infrastructure.persistance.repository.TeacherEtablissementRepository
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.*
import emy.backend.lawapp50.app.user.application.service.AccountUserService
import emy.backend.lawapp50.app.user.domain.model.AccountUser
import emy.backend.lawapp50.route.actor.*
import emy.backend.lawapp50.security.*
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.*
import jakarta.servlet.http.*
import jakarta.validation.*
import kotlinx.coroutines.*
import org.slf4j.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Profile("dev")
class TeacherController(
    private val service : TeacherService,
    private val account : AccountUserService,
    private val auth: Auth,
    private val sentry : SentryService,
    private val etablissementRepository: EtablissementRepository,
    private val promotionRepository: PromotionRepository,
    private val teacherEtablissementRepository: TeacherEtablissementRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    @Operation(summary = "Liste des Enseignatn")
    @GetMapping("/{version}/${TeacherScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllTeacher(request: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("teachers" to service.finAll())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.account.getAllTeacher.count",
                    distributionName = "api.account.getAllTeacher.latency"
                )
            )
        }
    }

    @Operation(summary = "Création des Enseignants")
    @PostMapping("/{version}/${TeacherScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createTeacher(request: HttpServletRequest, @Valid @RequestBody data2: TeacherRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        val userConnect = auth.user()
        try {
            val data = data2.toDomain(userConnect?.first?.userId)
//            if (data2.etablissement.isNotEmpty()) {
            val teacher = service.create(data)
            val state = account.save(AccountUser(userId = data.userId!!, accountId = 3))
            data2.etablissement.forEach {
                teacherEtablissementRepository.save(TeacherEtablissementEntity(teacherId = teacher.teacherId!!, etablissementId = it.etablisementId))
            }
            ResponseEntity.status(201).body(mapOf(
                "message" to "Compte enseignant assigné avec succès",
                "teachers" to teacher
            ))
//            }
//            ResponseEntity.status(404).body(mapOf("message" to "Etablissement manquante"))

        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.account.createTeacher.count",
                    distributionName = "api.account.createTeacher.latency"
                )
            )
        }
    }
}