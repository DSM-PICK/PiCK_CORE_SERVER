package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.afterschool.port.out.DeleteAfterSchoolStudentPort
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.attendance.port.`in`.ResetAttendanceUseCase
import dsm.pick2024.domain.classroom.port.out.DeleteClassRoomPort
import dsm.pick2024.domain.status.port.`in`.ResetStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.ResetWeekendMealUseCase
import dsm.pick2024.infrastructure.batch.config.BatchConfig
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduleService(
    private val deleteClassRoomPort: DeleteClassRoomPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val deleteAfterSchoolStudentPort: DeleteAfterSchoolStudentPort,
    private val resetAttendanceUseCase: ResetAttendanceUseCase,
    private val resetWeekendMealUseCase: ResetWeekendMealUseCase,
    private val resetStatusUseCase: ResetStatusUseCase,
    private val batchConfig: BatchConfig,
    private val jobLauncher: JobLauncher
) {
    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")
    fun deleteTable() {
        deleteClassRoomPort.deleteAll()
        deleteApplicationPort.deleteAll()
        deleteAfterSchoolStudentPort.deleteAll()
    }

    @Scheduled(cron = "0 0 0 25 * ?", zone = "Asia/Seoul")
    fun monthSchedule() {
        val jobParameters = JobParametersBuilder()
            .toJobParameters()
        jobLauncher.run(batchConfig.neisMealJob(), jobParameters)
        resetWeekendMealUseCase.resetWeekendMeal()
    }

    @Scheduled(cron = "0 00 21 * * ?", zone = "Asia/Seoul")
    fun resetTable() {
        resetStatusUseCase.reset()
        resetAttendanceUseCase.resetAttendance()
    }

    @Scheduled(cron = "0 0 14 * * SUN")
    fun saveTimetable() {
        deleteTimetablePort.deleteAll()
        saveTimetableUseCase.saveTimetable()
    }

    @Scheduled(cron = "0 0 8 * * ?")
    fun saveSchedule() {
        saveScheduleUseCase.saveNeisInfoToDatabase()
    }
}
