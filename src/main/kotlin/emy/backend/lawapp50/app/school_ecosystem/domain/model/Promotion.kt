package emy.backend.lawapp50.app.school_ecosystem.domain.model

import emy.backend.lawapp50.app.school_ecosystem.infrastructure.persistance.entity.PromotionEntity

data class Promotion (
    val id: Long? = null,
    val name: String,
    val isActive: Boolean = true
)

fun Promotion.toEntity() = PromotionEntity(
    id = this.id,
    name = this.name
)