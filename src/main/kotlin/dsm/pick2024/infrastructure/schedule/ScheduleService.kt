package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.application.port.out.DeleteAllApplicationPort
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteAllClassRoomPort
import dsm.pick2024.domain.earlyreturn.port.out.DeleteAllEarlyReturnPort
import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleService(
    private val deleteAllClassRoomPort: DeleteAllClassRoomPort,
    private val deleteAllEarlyReturnPort: DeleteAllEarlyReturnPort,
    private val deleteAllApplicationPort: DeleteAllApplicationPort,
    private val mealUseCase: MealUseCase,
    private val resetAttendanceUseCase: ResetAttendanceUseCase
) {
    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")
    fun deleteTable() {
        deleteAllClassRoomPort.deleteAll()
        deleteAllEarlyReturnPort.deleteAll()
        deleteAllApplicationPort.deleteAll()
    }

    @Scheduled(cron = "0 0 0 20 * ?", zone = "Asia/Seoul")
    fun mealSave() {
        mealUseCase.saveNeisInfoToDatabase()
    }

    @Scheduled(cron = "0 00 21 * * ?", zone = "Asia/Seoul")
    fun resetTable() {
        resetAttendanceUseCase.reset()
    }
}
