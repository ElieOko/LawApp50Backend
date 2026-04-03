package emy.backend.lawapp50.app.evaluation.infrastructure.controller

import emy.backend.lawapp50.app.evaluation.application.service.*
import emy.backend.lawapp50.app.evaluation.domain.request.*
import emy.backend.lawapp50.app.user.application.service.*
import emy.backend.lawapp50.route.evaluation.*
import emy.backend.lawapp50.security.*
import io.swagger.v3.oas.annotations.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import emy.backend.lawapp50.security.monitoring.*
import emy.backend.lawapp50.utils.Response.RESSOURCE_NOT_ALLOW
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.*
import jakarta.validation.*
import org.springframework.web.server.ResponseStatusException

@Tag(name = "Evaluation", description = "Gestion des evaluations")
@RestController
@RequestMapping
@Profile("dev")
class EvaluationController(
    private val service: EvaluationService,
    private val accountUserService: AccountUserService,
    private val sentry: SentryService,
    private val auth: Auth,
    private val questionOuverteService: QuestionOuverteService,
    private val questionOptionService: QuestionOptionService,
    private val questionCaseStudyService: QuestionCaseStudyService,
    private val questionService: QuestionService,
) {
    @Operation(summary = "Création de la session d'evaluation")
    @PostMapping("/{version}/${EvaluationScope.PRIVATE}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createSessionEvaluation(
        request : HttpServletRequest,
        @Valid @RequestBody data : EvaluationRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        val session = auth.user()
        try {
            val account = accountUserService.findMultipleAccountUser(session?.first?.userId?:0)
            if (account.isNotEmpty()){
                if (account[0].accountId == 3L){
                    if (data.ouverte?.isEmpty() == true && data.option?.isEmpty() == true  && data.caseStudy?.isEmpty() == true) throw ResponseStatusException(HttpStatusCode.valueOf(404), "Vous devez avoir au moins des questions avant de créer vos évaluations.")
                    val state = service.create(data.toDomain(session?.first?.userId!!))
                    data.option?.forEach {qt->
                        val questionSave = questionService.create(qt.question.toDomain(state.id!!))
                        qt.questionOption?.forEach { questionOptionService.create(it.toDomain(questionSave.id!!)) }
                    }
                    data.ouverte?.forEach { qt->
                        val questionSave = questionService.create(qt.question.toDomain(state.id!!))
                        qt.questionOuverte?.forEach { questionOuverteService.create(it.toDomain(questionSave.id!!)) }
                    }
                    data.caseStudy?.forEach { qt->
                        val questionSave = questionService.create(qt.question.toDomain(state.id!!))
                        qt.questionCaseStudy?.forEach { questionCaseStudyService.create(it.toDomain(questionSave.id!!))}
                    }
                    ResponseEntity.ok(mapOf("message" to "Votre évaluation a été créer avec succès"))
                }
                else{
                    ResponseStatusException(HttpStatusCode.valueOf(403), "$RESSOURCE_NOT_ALLOW, vous n'êtes pas enseignant.")
                }
            } else {
                ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            }
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.evaluation.createSessionEvaluation.count",
                    distributionName = "api.evaluation.createSessionEvaluation.latency"
                )
            )
        }
    }

    @Operation(summary = "Liste des evaluations")
    @GetMapping("/{version}/${EvaluationScope.PUBLIC}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllEvaluation(request: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {

        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.evaluation.getAllEvaluation.count",
                    distributionName = "api.evaluation.getAllEvaluation.latency"
                )
            )
        }
    }


}