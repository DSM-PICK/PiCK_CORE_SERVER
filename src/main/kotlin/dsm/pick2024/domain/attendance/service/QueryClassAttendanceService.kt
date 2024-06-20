package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.ExistClassRoomPort
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassroomPort: QueryClassroomPort,
    private val existClassRoomPort: ExistClassRoomPort
) : QueryClassAttendanceUseCase {

    @Transactional(readOnly = true)
    override fun queryClassAttendance(
        grade: Int,
        classNum: Int
    ) =
        queryAttendancePort.findByGradeAndClassNum(grade, classNum)
            .map { it ->
                val userId = it.userId
                val classroomName =
                    existClassRoomPort.existOKByUserId(userId)
                        .takeIf { it }
                        ?.let {
                            queryClassroomPort.findOKClassroom(userId)?.classroomName
                        } ?: "" //예외처리 만들기

                with(it) {
                    QueryAttendanceResponse(
                        id = userId,
                        username = userName,
                        grade = grade,
                        classNum = classNum,
                        num = num,
                        status6 = period6,
                        status7 = period7,
                        status8 = period8,
                        status9 = period9,
                        status10 = period10,
                        classroomName = classroomName
                    )
                }
            }
}
