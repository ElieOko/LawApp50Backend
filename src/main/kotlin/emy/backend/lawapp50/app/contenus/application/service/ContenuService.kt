package emy.backend.lawapp50.app.contenus.application.service

import emy.backend.lawapp50.app.contenus.domain.model.ContenuDto
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.entity.ContenuEntity
import emy.backend.lawapp50.app.contenus.infrastructure.persistance.repository.ContenuRepository
import emy.backend.lawapp50.app.user.application.service.UserService
import emy.backend.lawapp50.app.user.infrastructure.persistance.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class ContenuService(
    private val r: ContenuRepository,
    private val userR: UserRepository,
    private val userS : UserService,
    private val typeCS: TypeContenuService,
    private val scopC: ScopeContenuService
) {
    suspend fun create(c: ContenuEntity): ContenuEntity{
       return r.save(c)
    }

    suspend fun findById(id:Long): ContenuEntity?{
        return r.findById(id)
    }

    suspend fun getAll(): List<ContenuEntity>{
        return r.findAll().map{it}.toList()
    }

    suspend fun toDtoEntity(it: ContenuEntity): ContenuDto{
        var userCheck = userR.findById(it.userId)
        val user = if (userCheck != null) userS.findIdUser(it.userId) else null
        val scope = scopC.getScopeContenu(it.id!!)?.map { it.scope }?.toList()
        return ContenuDto(
            contenu = it,
            typeContenu =typeCS.findById(it.typeContenuId!!) ,
            scope = scope,
            user = user
        )
    }
}