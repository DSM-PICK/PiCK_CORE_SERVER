package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.QueryAdminAllUseCase
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import org.springframework.stereotype.Service

@Service
class QueryAdminAllService(
    private val queryAdminPort: QueryAdminPort
) : QueryAdminAllUseCase {
    override fun queryAdminAll(): List<String> =
        queryAdminPort.findAll().map { it.name }
}
