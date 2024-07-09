package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.Status
import java.util.*

interface QueryApplicationPort {

    fun findById(id: UUID): Application?

    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Application>

    fun findByFloorAndStatus(floor: Int, status: Status): List<Application>

    fun findOKApplication(id: UUID): Application?

    fun findByUserId(userId: UUID): Application?

    fun queryApplicationWithAttendance(floor: Int): List<Application>
}
