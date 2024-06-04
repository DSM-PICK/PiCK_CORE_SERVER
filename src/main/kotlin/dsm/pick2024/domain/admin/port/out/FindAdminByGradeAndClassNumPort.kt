package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface FindAdminByGradeAndClassNumPort {
    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): Admin?
}
