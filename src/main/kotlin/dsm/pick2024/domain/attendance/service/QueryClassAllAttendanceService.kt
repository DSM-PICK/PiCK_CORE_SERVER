package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.attendance.port.`in`.QueryClassAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.presentation.dto.response.QueryAllAttendanceResponse
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryClassAllAttendanceService(
    private val queryAttendancePort: QueryAttendancePort,
    private val classroomFinderUseCase: ClassroomFinderUseCase
) : QueryClassAllAttendanceUseCase {
    @Transactional(readOnly = true)
    override fun queryClassAllAttendance(grade: Int, classNum: Int) =
        queryAttendancePort.findByGradeAndClassNum(grade, classNum)
            .map { it ->
                val userId = it.userId
                val classroomName = try {
                    val classroom = classroomFinderUseCase.findOKClassroomOrThrow(userId)
                    classroom.classroomName ?: ""
                } catch (e: EmptyResultDataAccessException) {
                    ""
                }

                with(it) {
                    QueryAllAttendanceResponse(
                        it,
                        classroomName
                    )
                }
            }.distinctBy { it.id }.sortedWith(compareBy { it.num })
}
