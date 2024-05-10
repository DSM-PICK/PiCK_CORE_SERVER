package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryClassAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.ExistsByUserIdPort
import dsm.pick2024.domain.classroom.port.out.FindOKClassroomPort
import org.springframework.stereotype.Service

@Service
class QueryClassAttendanceService(
    private val queryClassAttendancePort: QueryClassAttendancePort,
    private val findOKClassroomPort: FindOKClassroomPort,
    private val existsByUserIdPort: ExistsByUserIdPort
) : QueryClassAttendanceUseCase {
    override fun queryClassAttendance(
        grade: Int,
        classNum: Int
    ) = queryClassAttendancePort.findByGradeAndClassNum(grade, classNum)
        .map { it ->
            val userId = it.userId
            val classroomName =
                if (existsByUserIdPort.existsByUserId(userId)) {
                    val classroom = findOKClassroomPort.findOKClassroom(userId)
                    classroom!!.classroomName
                } else {
                    ""
                }

            QueryAttendanceResponse(
                id = it.userId,
                username = it.name,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status6 = it.period6,
                status7 = it.period7,
                status8 = it.period8,
                status9 = it.period9,
                status10 = it.period10,
                classroomName = classroomName
            )
        }
}
