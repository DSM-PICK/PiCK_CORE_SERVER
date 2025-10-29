package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.attendance.port.`in`.AttendanceFinderUseCase
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.`in`.QueryGradeClassroomUseCase
import dsm.pick2024.domain.classroom.presentation.dto.response.QueryClassroomResponse
import org.joda.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryGradeClassroomService(
    private val classroomFinderUseCase: ClassroomFinderUseCase,
    private val attendanceFinderUseCase: AttendanceFinderUseCase
) : QueryGradeClassroomUseCase {
    @Transactional(readOnly = true)
    override fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ): List<QueryClassroomResponse> {
        val today = LocalDate.now().dayOfWeek

        return classroomFinderUseCase.queryGradeClassroomOrThrow(grade, classNum)
            .map { classroom ->
                val move = if (today == 2 || today == 5) {
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
