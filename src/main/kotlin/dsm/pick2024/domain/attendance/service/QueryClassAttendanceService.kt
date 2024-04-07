package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryClassAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAttendanceResponse
import org.springframework.stereotype.Service

@Service
class QueryClassAttendanceService(
    private val queryClassAttendancePort: QueryClassAttendancePort
) : QueryClassAttendanceUseCase {
    override fun queryClassAttendance(
        grade: Int,
        classNum: Int
    ) = queryClassAttendancePort.findByGradeAndClassNum(grade, classNum)
        .map { it ->
            QueryAttendanceResponse(
                id = it.userId,
                username = it.name,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                status8 = it.period8,
                status9 = it.period9,
                status10 = it.period10
            )
        }
}
