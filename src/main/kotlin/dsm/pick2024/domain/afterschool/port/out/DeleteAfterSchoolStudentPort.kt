package dsm.pick2024.domain.afterschool.port.out

import java.util.*

interface DeleteAfterSchoolStudentPort {

    fun deleteById(id: UUID)

    fun deleteAll()
}
