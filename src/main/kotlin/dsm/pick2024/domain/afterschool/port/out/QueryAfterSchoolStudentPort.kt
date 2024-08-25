package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import java.util.*

interface QueryAfterSchoolStudentPort {

    fun findByUserId(id: UUID): AfterSchoolStudent?

    fun findAll(): List<AfterSchoolStudent>

    fun findById(id: UUID): AfterSchoolStudent?
}
