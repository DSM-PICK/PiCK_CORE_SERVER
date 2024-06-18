package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.domain.Status
import java.util.*

interface QueryStatusPort {
    fun findAll(): List<Status>

    fun findStatusByUserId(id: UUID): Status?

    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Status>
}
