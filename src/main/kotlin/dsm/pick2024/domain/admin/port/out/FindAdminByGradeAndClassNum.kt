package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface FindAdminByGradeAndClassNum {
    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): Admin?
}
