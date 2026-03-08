package emy.backend.lawapp50.app.actor.infrastructure.controller

import emy.backend.lawapp50.app.actor.application.service.*
import emy.backend.lawapp50.app.actor.domain.model.*
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

//@RestController
//@RequestMapping
//@Profile("dev")
//class TeacherController(
//    private val service : TeacherService,
//    private val account : AccountUserService,
//    private val auth: Auth,
//    private val sentry : SentryService,
//    private val etablissementRepository: EtablissementRepository,
//    private val promotionRepository: PromotionRepository
//) {
//    private val log = LoggerFactory.getLogger(this::class.java)
//    @Operation(summary = "Liste des Etudiants")
//    @GetMapping("/{version}/${StudentScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
//    suspend fun getAllTeacher(request: HttpServletRequest) = coroutineScope {
//        val startNanos = System.nanoTime()
//        try {
//            mapOf("teachers" to service.finAll())
//        } finally {
//            sentry.callToMetric(
//                MetricModel(
//                    startNanos = startNanos,
//                    status = "200",
//                    route = "${request.method} /${request.requestURI}",
//                    countName = "api.account.getallTeacher.count",
//                    distributionName = "api.account.getallTeacher.latency"
//                )
//            )
//        }
//    }
//
//    @Operation(summary = "Création des Enseignants")
//    @PostMapping("/{version}/${StudentScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
//    suspend fun createTeacher(request: HttpServletRequest, @Valid @RequestBody data: TeacherRequest) = coroutineScope {
//        val startNanos = System.nanoTime()
//        val userConnect = auth.user()
//        try {
////            data.userId = userConnect?.first?.userId
////            data
////            var etablissement :Long? = null
////            if (data.typeTeacherId != null) {
////                etablissement = etablissementRepository.findById(data.etablissementId!!)?.id
////            }
////            if (data.promotionId == null) ResponseEntity.status(404).body(mapOf("message" to "Promotion introuvable"))
////
////            val promotion = promotionRepository.findById(data.promotionId!!)?.id
////            data.etablissementId = etablissement
////            data.promotionId = promotion
////            val student = service.create(data)
////            val state = account.save(AccountUser(userId = data.userId!!, accountId = 2))
//            ResponseEntity.status(201).body(mapOf(
//                "message" to "Compte enseignant assigné avec succès",
//                "teachers" to ""
//            ))
//
//        } finally {
//            sentry.callToMetric(
//                MetricModel(
//                    startNanos = startNanos,
//                    status = "200",
//                    route = "${request.method} /${request.requestURI}",
//                    countName = "api.account.createStudent.count",
//                    distributionName = "api.account.createStudent.latency"
//                )
//            )
//        }
//    }
//}