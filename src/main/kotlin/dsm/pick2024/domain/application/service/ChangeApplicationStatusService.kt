package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ChangeApplicationStatusUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.util.UUID

@Service
class ChangeApplicationStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort,
    private val attendanceService: AttendanceService
) : ChangeApplicationStatusUseCase {

    @Transactional
    override fun changeStatusApplication(request: ApplicationStatusRequest) {
        if (request.status == Status.NO) {
            handleStatusNo(request.ids)
            return
        }

        val admin = adminFacadeUseCase.currentAdmin()
        val updatedApplications = request.ids.map { id ->
            val application = findApplicationById(id)
            createUpdatedApplication(application, admin.name)
        }

        val applicationStories = updatedApplications.map { application ->
            createApplicationStory(application)
        }

        val attendance = updatedApplications.map { it ->
            val attendanceId = queryAttendancePort.findByUserId(it.userId)
            attendanceService.translateApplicationTime(it.startTime, it.endTime, attendanceId!!)
        }.toMutableList()

        saveApplicationPort.saveAll(updatedApplications)
        applicationStorySaveAllPort.saveAll(applicationStories)
        saveAttendancePort.saveAll(attendance)
    }

    private fun handleStatusNo(ids: List<UUID>) {
        ids.forEach { id ->
            val application = findApplicationById(id)
            deleteApplicationPort.deleteById(application.id!!)
        }
    }

    private fun findApplicationById(id: UUID): Application {
        return queryApplicationPort.findById(id) ?: throw ApplicationNotFoundException
    }

    private fun createUpdatedApplication(application: Application, adminName: String): Application {
        return application.copy(
            teacherName = adminName,
            status = Status.OK
        )
    }

    private fun createApplicationStory(application: Application): ApplicationStory {
        return ApplicationStory(
            reason = application.reason,
            userName = application.userName,
            startTime = application.startTime,
            endTime = application.endTime,
            date = application.date,
            type = Type.APPLICATION,
            userId = application.userId
        )
    }
}
