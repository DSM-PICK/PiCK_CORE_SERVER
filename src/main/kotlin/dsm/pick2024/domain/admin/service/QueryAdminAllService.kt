package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.admin.port.`in`.QueryAdminAllUseCase
import org.springframework.stereotype.Service

@Service
class QueryAdminAllService(
    private val adminFinderUseCase: AdminFinderUseCase
) : QueryAdminAllUseCase {
    override fun queryAdminAll(): List<String> =
        adminFinderUseCase.findAllOrThrow().map { it.name }
}
