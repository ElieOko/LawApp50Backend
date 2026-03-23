package server.web.casa.app.user.infrastructure.controller

import emy.backend.lawapp50.app.school_ecosystem.application.service.*
import emy.backend.lawapp50.app.school_ecosystem.domain.request.*
import emy.backend.lawapp50.app.user.application.service.*
import emy.backend.lawapp50.route.tp.*
import emy.backend.lawapp50.security.*
import io.swagger.v3.oas.annotations.*
import kotlinx.coroutines.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import emy.backend.lawapp50.security.monitoring.*
import emy.backend.lawapp50.utils.Response.RESSOURCE_NOT_ALLOW
import jakarta.servlet.http.*
import jakarta.validation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api")
@Profile("dev")
class TravailPratiqueController(
    private val service: TravailPratiqueService,
    private val etsService: EtablissementService,
    private val promotionService: PromotionService,
    private val accountUserService: AccountUserService,
    private val faculteService: FaculteService,
    private val sentry: SentryService,
    private val auth: Auth,
) {
    @Operation(summary = "Création de TP")
    @PostMapping("/{version}/${TpScope.PRIVATE}", consumes = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun createTP(
        request : HttpServletRequest,
        @Valid @RequestBody data : TravailPratiqueRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        val session = auth.user()
        try {
            etsService.findById(data.tp.etablissementId)
            promotionService.findById(data.tp.promotionId)
            faculteService.findById(data.tp.faculteId)
            val account = accountUserService.findMultipleAccountUser(session?.first?.userId?:0)
            if (account.isNotEmpty()){
                if (account[0].accountId == 3L){
                    val state = service.create(data.tp.toDomain(session?.first?.userId!!))
                    
                }
                else{
                    ResponseStatusException(HttpStatusCode.valueOf(403), "$RESSOURCE_NOT_ALLOW, vous n'êtes pas enseignant.")
                }
            } else {
                ResponseStatusException(HttpStatusCode.valueOf(403), RESSOURCE_NOT_ALLOW)
            }

//            mapOf("tps" to service.getAll().toList())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.tp.createTP.count",
                    distributionName = "api.tp.createTP.latency"
                )
            )
        }
    }
    @Operation(summary = "Liste de TP")
    @GetMapping("/{version}/${TpScope.PUBLIC}",produces = [MediaType.APPLICATION_JSON_VALUE])
    suspend fun getAllTP(request: HttpServletRequest) = coroutineScope {
        val startNanos = System.nanoTime()
        try {
//            mapOf("tps" to service.getAll().toList())
        } finally {
            sentry.callToMetric(
                MetricModel(
                    startNanos = startNanos,
                    status = "200",
                    route = "${request.method} /${request.requestURI}",
                    countName = "api.tp.getAllTP.count",
                    distributionName = "api.tp.getAllTP.latency"
                )
            )
        }
    }
}