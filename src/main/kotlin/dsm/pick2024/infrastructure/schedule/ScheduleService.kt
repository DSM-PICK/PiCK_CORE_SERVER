package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.meal.port.`in`.MealUseCase
import dsm.pick2024.domain.schedule.port.`in`.SaveScheduleUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SendNotificationSelfStudyTeacherUseCase
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.timetable.port.`in`.SaveTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.DeleteTimeTablePort
import dsm.pick2024.domain.weekendmeal.port.`in`.NotificationWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.UpdateWeekendMealUseCase
import dsm.pick2024.infrastructure.sse.port.out.SseRegistryPort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val deleteTimetablePort: DeleteTimeTablePort,
    private val mealUseCase: MealUseCase,
    private val resetAttendanceUseCase: ResetAttendanceUseCase,
    private val resetStatusUseCase: ResetStatusUseCase,
    private val saveTimetableUseCase: SaveTimetableUseCase,
    private val saveScheduleUseCase: SaveScheduleUseCase,
    private val updateWeekendMealUseCase: UpdateWeekendMealUseCase,
    private val sendNotificationSelfStudyTeacherUseCase: SendNotificationSelfStudyTeacherUseCase,
    private val notificationWeekendMealUseCase: NotificationWeekendMealUseCase,
    private val sseRegistryPort: SseRegistryPort
) {
    /**
     * Removes all classroom and application entries from their data stores.
     *
     * Runs on the scheduled cron (20:30 Asia/Seoul) to clear classroom and application data.
     */
    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")
    fun deleteTable() {
        deleteClassRoomPort.deleteAll()
        deleteApplicationPort.deleteAll()
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

    @Scheduled(cron = "0 0 8 * * ?", zone = "Asia/Seoul")
    fun saveSchedule() {
        saveScheduleUseCase.saveNeisInfoToDatabase()
    }

    @Scheduled(cron = "0 0 23 L * ?", zone = "Asia/Seoul")
    fun weekendMealStatusReset() {
        updateWeekendMealUseCase.execute()
    }

    @Scheduled(cron = "0 0 8 * * 1-5", zone = "Asia/Seoul")
    fun sendNotificationSelfStudyTeacher() {
        sendNotificationSelfStudyTeacherUseCase.execute()
    }

    /**
     * Sends notifications about weekend meals to recipients.
     *
     * Scheduled to run daily at 08:00 Asia/Seoul to trigger weekend-meal notification delivery.
     */
    @Scheduled(cron = "0 0 8 * * *", zone = "Asia/Seoul")
    fun notificationWeekendMeal() {
        notificationWeekendMealUseCase.execute()
    }

    /**
     * Sends a heartbeat signal to the server-sent events registry to keep client connections active.
     */
    @Scheduled(cron = "0/30 * 8-21 * * 1-5", zone = "Asia/Seoul")
    fun sseHeartbeat() {
        sseRegistryPort.sendHeartbeat()
    }
}