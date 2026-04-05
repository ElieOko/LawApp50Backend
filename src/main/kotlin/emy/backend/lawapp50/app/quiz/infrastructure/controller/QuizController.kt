package emy.backend.lawapp50.app.quiz.infrastructure.controller

import emy.backend.lawapp50.app.quiz.application.service.QuizService
import emy.backend.lawapp50.app.quiz.domain.request.QuizRequest
import emy.backend.lawapp50.app.user.application.service.AccountUserService
import emy.backend.lawapp50.route.quiz.QuizScope
import emy.backend.lawapp50.security.Auth
import emy.backend.lawapp50.security.monitoring.MetricModel
import emy.backend.lawapp50.security.monitoring.SentryService
import emy.backend.lawapp50.utils.Response.RESSOURCE_NOT_ALLOW
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import kotlinx.coroutines.coroutineScope
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@Tag(name = "Quiz", description = "Quiz structurés par niveaux (QCM)")
@RestController
@RequestMapping
@Profile("dev")
class QuizController(
    private val quizService: QuizService,
    private val auth: Auth,
    private val accountUserService: AccountUserService,
    private val sentry: SentryService,
) {
    @Operation(summary = "Création d'un quiz avec niveaux et questions")
    @PostMapping("/{version}/${QuizScope.PRIVATE}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createQuiz(
        request: HttpServletRequest,
        @Valid @RequestBody data: QuizRequest,
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val session = auth.user()
            val userId = session?.first?.userId
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            val account = accountUserService.findMultipleAccountUser(userId)
            if (account.isEmpty()) {
                throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            }
            if (account[0].accountId != 3L) {
                throw ResponseStatusException(
                    HttpStatusCode.valueOf(403),
                    "$RESSOURCE_NOT_ALLOW, vous n'êtes pas enseignant.",
                )
            }
            val detail = quizService.createFromRequest(userId, data)
            ResponseEntity.status(201).body(
                mapOf(
                    "message" to "Quiz créé avec succès",
                    "quiz" to detail,
                ),
            )
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.quiz.create.count",
                    distributionName = "api.quiz.create.latency",
                ),
            )
        }
    }

    @Operation(summary = "Liste des quiz actifs")
    @GetMapping("/{version}/${QuizScope.PROTECTED}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun listQuizzes(request: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("quizzes" to quizService.listActive())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.quiz.list.count",
                    distributionName = "api.quiz.list.latency",
                ),
            )
        }
    }

    @Operation(summary = "Détail d'un quiz (niveaux, questions, options)")
    @GetMapping("/{version}/${QuizScope.PRIVATE}/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getQuizById(request: HttpServletRequest, @PathVariable id: Long) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("quiz" to quizService.findDetail(id))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.quiz.getbyid.count",
                    distributionName = "api.quiz.getbyid.latency",
                ),
            )
        }
    }
}
