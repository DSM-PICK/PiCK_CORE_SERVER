package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.afterschool.port.out.DeleteAfterSchoolStudentPort
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import dsm.pick2024.domain.schedule.port.`in`.SaveScheduleUseCase
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.timetable.port.`in`.SaveTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.DeleteTimeTablePort
import dsm.pick2024.domain.weekendmeal.port.`in`.UpdateWeekendMealUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val deleteAfterSchoolStudentPort: DeleteAfterSchoolStudentPort,
    private val deleteTimetablePort: DeleteTimeTablePort,
    private val mealUseCase: MealUseCase,
    private val resetAttendanceUseCase: ResetAttendanceUseCase,
    private val resetStatusUseCase: ResetStatusUseCase,
    private val saveTimetableUseCase: SaveTimetableUseCase,
    private val saveScheduleUseCase: SaveScheduleUseCase,
    private val updateWeekendMealUseCase: UpdateWeekendMealUseCase,
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
    }

    @Scheduled(cron = "0 00 21 * * ?", zone = "Asia/Seoul")
    fun resetTable() {
        resetStatusUseCase.reset()
        resetAttendanceUseCase.resetAttendance()
    }

    @Scheduled(cron = "0 0 6-23 * * 1-5", zone = "Asia/Seoul")
    fun saveTimetable() {
        deleteTimetablePort.deleteAll()
        saveTimetableUseCase.saveTimetable(0)
    }

    @Scheduled(cron = "0 0 2 * * 6", zone = "Asia/Seoul")
    fun saveNextWeekTimeTable() {
        deleteTimetablePort.deleteAll()
        saveTimetableUseCase.saveTimetable(4)
    }

    @Scheduled(cron = "0 0 8 * * ?")
    fun saveSchedule() {
        saveScheduleUseCase.saveNeisInfoToDatabase()
    }

    @Scheduled(cron = "0 0 23 L * ?")
    fun weekendMealStatusReset(){
        updateWeekendMealUseCase.execute()
    }


}
