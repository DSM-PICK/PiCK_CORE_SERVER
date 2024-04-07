package dsm.pick2024.domain.attendance.service

import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.port.out.SaveAll
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAllAttendanceSaveAllUserService(
    private val saveAll: SaveAll,
    private val xquareFeignClient: XquareFeignClient
) : SaveAllAttendanceUseCase {
    @Transactional
    override fun saveAll(key: String) {
        val xquare = xquareFeignClient.userAll(key)
        val entity =
            xquare.map {
                    it ->
                Attendance(
                    userId = it.id,
                    name = it.name,
                    grade = it.grade,
                    classNum = it.classNum,
                    num = it.num,
                    club = it.club,
                    period6 = Status.ATTENDANCE,
                    period7 = Status.ATTENDANCE,
                    period8 = Status.ATTENDANCE,
                    period9 = Status.ATTENDANCE,
                    period10 = Status.ATTENDANCE
                )
            }.toMutableList()
        saveAll.saveAll(entity)
    }
}
