package emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.repository

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.EtablissementEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EtablissementRepository  : CoroutineCrudRepository<EtablissementEntity, Long>{

}
