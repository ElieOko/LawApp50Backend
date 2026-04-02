package emy.backend.lawapp50.app.evaluation.infrastructure.persistance.repository

import emy.backend.lawapp50.app.evaluation.infrastructure.persistance.entity.EvaluationEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface EvaluationRepository  : CoroutineCrudRepository<EvaluationEntity, Long>{}
