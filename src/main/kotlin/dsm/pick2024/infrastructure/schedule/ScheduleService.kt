package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.afterschool.port.out.DeleteAfterSchoolStudentPort
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.ResetWeekendMealUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val deleteAfterSchoolStudentPort: DeleteAfterSchoolStudentPort,
    private val mealUseCase: MealUseCase,
    private val resetAttendanceUseCase: ResetAttendanceUseCase,
    private val resetWeekendMealUseCase: ResetWeekendMealUseCase,
    private val resetStatusUseCase: ResetStatusUseCase
) {
    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")
    fun deleteTable() {
        deleteClassRoomPort.deleteAll()
        deleteApplicationPort.deleteAll()
        deleteAfterSchoolStudentPort.deleteAll()
    }

    @Scheduled(cron = "0 0 0 25 * ?", zone = "Asia/Seoul")
    fun monthSchedule() {
        mealUseCase.saveNeisInfoToDatabase()
        resetWeekendMealUseCase.resetWeekendMeal()
    }

    @Scheduled(cron = "0 00 21 * * ?", zone = "Asia/Seoul")
    fun resetTable() {
        resetAttendanceUseCase.resetAttendance()
        resetStatusUseCase.reset()
    }
}
