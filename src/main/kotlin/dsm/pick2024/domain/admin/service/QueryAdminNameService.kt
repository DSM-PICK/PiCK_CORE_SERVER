package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.admin.port.`in`.QueryAdminNameUseCase
import dsm.pick2024.domain.admin.presentation.dto.response.QueryAdminNameResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryAdminNameService(
    private val adminFacadeUseCase: AdminFacadeUseCase
) : QueryAdminNameUseCase {
    @Transactional(readOnly = true)
    override fun queryAdminName(): QueryAdminNameResponse {
        val admin = adminFacadeUseCase.currentUser()
        return QueryAdminNameResponse(
            name = admin.name,
            grade = admin.grade!!,
            classNum = admin.classNum!!
        )
    }
}
