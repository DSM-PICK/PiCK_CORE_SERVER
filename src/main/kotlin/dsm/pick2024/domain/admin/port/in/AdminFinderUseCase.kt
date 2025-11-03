package dsm.pick2024.domain.admin.port.`in`

import dsm.pick2024.domain.admin.domain.Admin

interface AdminFinderUseCase {
    fun findByGradeAndClassNumOrThrow(grade: Int, classNum: Int): Admin

    fun findByAdminIdOrThrow(adminId: String): Admin

    fun findByAdminByNameOrThrow(name: String): Admin
}
