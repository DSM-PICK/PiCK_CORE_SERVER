package dsm.pick2024.domain.admin.port.out

import dsm.pick2024.domain.admin.domain.Admin

interface QueryAdminPort {

    fun findByGradeAndClassNum(grade: Int, classNum: Int): Admin?

    fun findAll(): List<Admin>

    fun findByAdminId(adminId: String): Admin?

    fun findBYAdminByName(name: String): Admin?
}
