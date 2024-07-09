package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import java.util.*

interface QueryApplicationPort {

    fun findById(id: UUID): Application?

    fun findByGradeAndClassNum(grade: Int, classNum: Int): List<Application>

    fun findByFloor(floor: Int): List<Application>

    fun findOKApplication(id: UUID): Application?

    fun findByUserId(userId: UUID): Application?

    fun queryApplicationWithAttendance(floor: Int): List<Application>
}
