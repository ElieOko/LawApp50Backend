package emy.backend.lawapp50.app.actor.infrastructure.controller

import emy.backend.lawapp50.app.actor.application.service.StudentService
import emy.backend.lawapp50.app.actor.domain.model.Student
import emy.backend.lawapp50.route.actor.StudentScope
import emy.backend.lawapp50.security.Auth
import emy.backend.lawapp50.security.monitoring.*
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.*
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.slf4j.*
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
@Profile("dev")
class StudentController(
    private val service : StudentService,
    private val auth: Auth,
    private val sentry : SentryService
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    @Operation(summary = "Liste des Etudiants")
    @GetMapping("/{version}/${StudentScope.PROTECTED}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllStudent(request: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("accounts" to service.finAllStudent())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.account.getallaccounte.count",
                    distributionName = "api.account.getallaccounte.latency"
                )
            )
        }
    }

    @Operation(summary = "Création des étudiants")
    @PostMapping("/{version}/${StudentScope.PRIVATE}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createStudent(request: HttpServletRequest, @Valid @RequestBody data: Student) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val student = service.create(data)
//            mapOf("accounts" to service.getAll().toList())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.account.getallaccounte.count",
                    distributionName = "api.account.getallaccounte.latency"
                )
            )
        }
    }
}