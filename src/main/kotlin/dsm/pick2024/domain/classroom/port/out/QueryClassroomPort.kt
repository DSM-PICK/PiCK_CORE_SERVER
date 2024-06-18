package dsm.pick2024.domain.classroom.port.out

import dsm.pick2024.domain.classroom.domain.Classroom
import java.util.*

interface QueryClassroomPort {

    fun findByUserId(userId: UUID): Classroom?

    fun findOKClassroom(id: UUID): Classroom?

    fun findAll(): List<Classroom>

    fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ): List<Classroom>

    fun queryFloorClassroom(floor: Int): List<Classroom>
}
