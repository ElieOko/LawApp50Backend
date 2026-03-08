package emy.backend.lawapp50.app.school_ecosystem.application.command

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.*
import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.*
import org.slf4j.*
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.*
import org.springframework.context.annotation.*
import org.springframework.core.annotation.*
import org.springframework.stereotype.*

@Component
@Order(5)
@Profile("dev")
class CommandSchoolEcosystem(
    @Value("\${spring.application.version}")  private val version: String,
    private val promotion: PromotionRepository,
    private val faculte: FaculteRepository,
    private val etablissement: EtablissementRepository,
) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun run(vararg args: String) {
        log.info("commande executor **User")
        log.info(this::class.simpleName)
        log.info(version)
        try {
            runBlocking {

            }
        }
        catch (e : Exception){
            log.info(e.message)
        }

    }
    suspend fun createAllFac(){
        val data = faculte.saveAll<FaculteEntity>(
            listOf(
//                FaculteEntity(name = "Droit"),
//                FaculteEntity(name = "Informatique"),
//                FaculteEntity(name = "Economie"),
//                FaculteEntity(name = "Médecine"),
//                FaculteEntity(name = "Polytechnique"),
//                FaculteEntity(name = "Lettres"),
//                FaculteEntity(name = "Sciences"),
//                FaculteEntity(name = "Architecture"),
            )
        ).toList()
        log.info("save fac all ${data.size}")
    }
    suspend fun createAllPromotion(){
        val data = promotion.saveAll<PromotionEntity>(
            listOf(
//                PromotionEntity(name = "L1"),
//                PromotionEntity(name = "L2"),
//                PromotionEntity(name = "L3"),
//                PromotionEntity(name = "L4"),
//                PromotionEntity(name = "M1"),
//                PromotionEntity(name = "M2"),
//                PromotionEntity(name = "M3"),
//                PromotionEntity(name = "D1"),
//                PromotionEntity(name = "D2"),
//                PromotionEntity(name = "D3")
            )
        ).toList()
        log.info("save promotion all ${data.size}")
    }
    suspend fun createAllEtablissement(){
        val data = etablissement.saveAll<EtablissementEntity>(
            listOf(
//                EtablissementEntity(name = "Université de Kinshasa (UNIKIN)"),
//                EtablissementEntity(name = "Université Pédagogique National (UPN)"),
//                EtablissementEntity(name = "Université Protestante au Congo (UPC)"),
//                EtablissementEntity(name = "Université Catholique du Congo (UCC)"),
//                EtablissementEntity(name = "Université Kongo (antenne Kinshasa)"),
//                EtablissementEntity(name = "Université Bel Campus"),
//                EtablissementEntity(name = "Université Libre de Kinshasa (ULK)"),
//                EtablissementEntity(name = "Université Révérent Kim (URK)"),
//                EtablissementEntity(name = "Université Chrétienne de Kinshasa (UCKIN)"),
//                EtablissementEntity(name = "Université de Bandundu (antenne Kinshasa)"),
//                EtablissementEntity(name = "Université de l'Assomption au Congo (UAC)"),
//                EtablissementEntity(name = "Université de la Renaissance"),
//                EtablissementEntity(name = "Université Loyola du Congo"),
//                EtablissementEntity(name = "Institut Facultaire des Sciences de l'Information et de la Communication (IFASIC)"),
//                EtablissementEntity(name = "Haute Ecole de Commerce de Kinshasa (HEC/KIN)"),
//                EtablissementEntity(name = "Institut National de Bâtiment et des Travaux Public (INBTP)"),
//                EtablissementEntity(name = "Institut Supérieur Pédagogique de Kinshasa (ISP/KIN)"),
//                EtablissementEntity(name = "Institut Supérieur d'Architecture et d'Urbanisme (ISAU)"),
//                EtablissementEntity(name = "Institut Supérieur des Techniques Médicales (ISTM/KIN)"),
//                EtablissementEntity(name = "Institut Supérieur des Beaux-Arts (ISBA)"),
//                EtablissementEntity(name = "Institut Supérieur des Techniques Appliquées (ISTA)"),
//                EtablissementEntity(name = "Institut Supérieur des Statistiques (ISS)"),
//                EtablissementEntity(name = "Institut Supérieur des Sciences Infirmières (ISSI)"),
//                EtablissementEntity(name = "Institut Supérieur des Sciences Agronomiques (ISSA)"),
//                EtablissementEntity(name = "Institut Supérieur d'Informatique Programmation et Analyse (ISIPA)"),
            )
        ).toList()
        log.info("save etablissement all ${data.size}")
    }

}