package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import java.util.UUID

interface FindAfterSchoolStudentByUserIdPort {
    fun findByUserId(id: UUID): AfterSchoolStudent?
}
