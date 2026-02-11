package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.classroom.port.`in`.QueryGradeClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import dsm.pick2024.global.common.TimeUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek

@Service
class QueryGradeClassroomService(
    private val queryClassroomPort: QueryClassroomPort,
    private val attendanceFinderUseCase: AttendanceFinderUseCase
) : QueryGradeClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ): List<QueryClassroomResponse> {
        val today = TimeUtils.nowLocalDate().dayOfWeek

        return queryClassroomPort.queryGradeClassroom(grade, classNum)
            .map { classroom ->
                val move = if (today == DayOfWeek.TUESDAY || today == DayOfWeek.FRIDAY) {
                    attendanceFinderUseCase.findByUserIdOrThrow(classroom.userId).place ?: ""
                } else {
                    "${classroom.grade}-${classroom.classNum}"
                }

                QueryClassroomResponse(
                    userId = classroom.userId,
                    userName = classroom.userName,
                    classroomName = classroom.classroomName,
                    move = move,
                    grade = classroom.grade,
                    classNum = classroom.classNum,
                    num = classroom.num,
                    start = classroom.startPeriod,
                    end = classroom.endPeriod
                )
            }.distinctBy { it.userId }.sortedWith(compareBy({ it.grade }, { it.classNum }, { it.num }))
    }
}
