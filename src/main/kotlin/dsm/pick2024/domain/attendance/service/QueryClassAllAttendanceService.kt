package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClassAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassAllAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val queryClassroomPort: QueryClassroomPort

) : QueryClassAllAttendanceUseCase {
    @Transactional(readOnly = true)
    override fun queryClassAllAttendance(grade: Int, classNum: Int) =
        queryAttendancePort.findByGradeAndClassNum(grade, classNum)
            .map { it ->
                val userId = it.userId
                val classroomName = try {
                    val classroom = queryClassroomPort.findOKClassroom(userId)
                    classroom?.classroomName ?: ""
                } catch (e: EmptyResultDataAccessException) {
                    ""
                }

                with(it) {
                    QueryAllAttendanceResponse(
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
                        classroomName = classroomName!!
                    )
                }
            }
}
