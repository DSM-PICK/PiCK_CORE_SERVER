package dsm.pick2024.domain.classroom.finder

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.ClassroomNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClassroomFinder(
    private val queryClassroomPort: QueryClassroomPort
) : ClassroomFinderUseCase {
    override fun findByUserIdOrThrow(userId: UUID) =
        queryClassroomPort.findByUserId(userId) ?: throw ClassroomNotFoundException

    override fun findOKClassroomOrThrow(id: UUID) =
        queryClassroomPort.findOKClassroom(id) ?: throw ClassroomNotFoundException

    override fun findAllOrThrow(): List<Classroom> =
        queryClassroomPort.findAll() ?: throw ClassroomNotFoundException

    override fun queryGradeClassroomOrThrow(grade: Int, classNum: Int): List<Classroom> =
        queryClassroomPort.queryGradeClassroom(grade, classNum) ?: throw ClassroomNotFoundException

    override fun queryFloorClassroomOrThrow(floor: Int): List<Classroom> =
        queryClassroomPort.queryFloorClassroom(floor) ?: throw ClassroomNotFoundException

    override fun findAllByStatusOrThrow(status: Status): List<Classroom> =
        queryClassroomPort.findAllByStatus(status) ?: throw ClassroomNotFoundException

    override fun queryFloorClassroomWithAttendanceOrThrow(floor: Int): List<Classroom> =
        queryClassroomPort.queryFloorClassroomWithAttendance(floor) ?: throw ClassroomNotFoundException
}
