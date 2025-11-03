package dsm.pick2024.domain.admin.finder

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import org.springframework.stereotype.Component

@Component
class AdminFinder(
    private val queryAdminPort: QueryAdminPort
) : AdminFinderUseCase {
    override fun findByGradeAndClassNumOrThrow(grade: Int, classNum: Int) =
        queryAdminPort.findByGradeAndClassNum(grade, classNum) ?: throw AdminNotFoundException

    override fun findByAdminIdOrThrow(adminId: String) =
        queryAdminPort.findByAdminId(adminId) ?: throw AdminNotFoundException

    override fun findByAdminByNameOrThrow(name: String) =
        queryAdminPort.findByAdminByName(name) ?: throw AdminNotFoundException
}
