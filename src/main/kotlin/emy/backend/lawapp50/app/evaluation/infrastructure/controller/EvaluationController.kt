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
@RequestMapping("api/{version}/")
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
    private val participationService: EvaluationParticipationService,
) {
    @Operation(summary = "Création de la session d'evaluation")
    @PostMapping(EvaluationScope.PRIVATE, consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createSessionEvaluation(
        request : HttpServletRequest,
        @Valid @RequestBody data : EvaluationRequest, @PathVariable version: String
    ) = coroutineScope {
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
                    throw ResponseStatusException(HttpStatusCode.valueOf(403), "$RESSOURCE_NOT_ALLOW, vous n'êtes pas enseignant.")
                }
            } else {
                throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
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
    @GetMapping(EvaluationScope.PUBLIC,produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllEvaluation(request: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("message" to "Liste des évaluations", "session" to service.getAllData())
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

    @Operation(summary = "Liste by ID")
    @GetMapping(EvaluationScope.PUBLIC+"/{id}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getEvaluationByID(request: HttpServletRequest, @PathVariable version: String, @PathVariable id: Long,) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            mapOf("message" to "Liste des évaluations", "session" to service.showDetail(id))
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

    @Operation(summary = "Liste des évaluations ouvertes auxquelles vous pouvez répondre (hors les vôtres)")
    @GetMapping("${EvaluationScope.PROTECTED}/repondre", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun listEvaluationsPourReponse(request: HttpServletRequest, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val session = auth.user()
            val userId = session?.first?.userId
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            mapOf("evaluations" to participationService.listAnswerableForUser(userId))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.evaluation.listPourReponse.count",
                    distributionName = "api.evaluation.listPourReponse.latency"
                )
            )
        }
    }

    @Operation(summary = "Feuille d'évaluation pour répondre (sans les bonnes réponses QCM)")
    @GetMapping("${EvaluationScope.PROTECTED}/{id}/passage", produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getPassageEvaluation(request: HttpServletRequest, @PathVariable id: Long, @PathVariable version: String) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val session = auth.user()
            val userId = session?.first?.userId
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            mapOf("evaluation" to participationService.buildPassage(id, userId))
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.evaluation.passage.count",
                    distributionName = "api.evaluation.passage.latency"
                )
            )
        }
    }

    @Operation(summary = "Soumettre les réponses à une évaluation (une seule fois, le créateur ne peut pas répondre)")
    @PostMapping(
        "${EvaluationScope.PROTECTED}/{id}/reponses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    suspend fun submitReponsesEvaluation(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @Valid @RequestBody body: EvaluationAnswerSubmitRequest, @PathVariable version: String,
    ) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
            val session = auth.user()
            val userId = session?.first?.userId
                ?: throw ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            val result = participationService.submitAnswers(id, userId, body)
            ResponseEntity.status(201).body(
                mapOf(
                    "message" to result.message,
                    "submissionId" to result.submissionId,
                    "scoreQcm" to result.scoreQcm,
                    "maxQcmPoints" to result.maxQcmPoints,
                ),
            )
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.evaluation.submitReponses.count",
                    distributionName = "api.evaluation.submitReponses.latency"
                )
            )
        }
    }

}