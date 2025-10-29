package dsm.pick2024.domain.classroom.port.`in`

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import java.util.*

interface ClassroomFinderUseCase {
    fun findByUserIdOrThrow(userId: UUID): Classroom

    fun findOKClassroomOrThrow(id: UUID): Classroom

    fun findAllOrThrow(): List<Classroom>

    fun queryGradeClassroomOrThrow(
        grade: Int,
        classNum: Int
    ): List<Classroom>

    fun queryFloorClassroomOrThrow(floor: Int): List<Classroom>

    fun findAllByStatusOrThrow(status: Status): List<Classroom>

    fun queryFloorClassroomWithAttendanceOrThrow(floor: Int): List<Classroom>
}
