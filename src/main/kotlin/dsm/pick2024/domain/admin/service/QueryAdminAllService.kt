package dsm.pick2024.domain.admin.service

import dsm.pick2024.domain.admin.port.`in`.QueryAdminAllUseCase
import dsm.pick2024.domain.admin.port.out.FindAllPort
import dsm.pick2024.domain.user.entity.enums.Role
import org.springframework.stereotype.Service

@Service
class QueryAdminAllService(
    private val findAllPort: FindAllPort
) : QueryAdminAllUseCase {
    override fun queryAdminAll(): List<String> = findAllPort.findAll().map { it.name }
}
