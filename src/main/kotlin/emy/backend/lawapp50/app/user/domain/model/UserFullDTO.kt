package emy.backend.lawapp50.app.user.domain.model

import emy.backend.lawapp50.app.user.infrastructure.persistance.entity.AccountDTO

data class UserFullDTO(
    val user: UserDto,
//    val accountMultiple: List<AccountDTO>,
    val profile: Any?,
//    val accounts: List<AccountDTO>,
//    val profile: Person?
)
