package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
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
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
        val admin = adminFacadeUseCase.currentAdmin()
        if (request.status == Status.NO) {
            handleStatusNo(request.ids)
            return
        }
        
        val updateApplications = request.ids.map { id ->
            val application = findApplicationById(id)
            updateApplication(application, admin.name)
        }

        val applicationStory = updateApplications.map { it ->
            createApplicationStory(it)
        }

        val attendance = updateApplications.map { it ->
            val attendanceId = queryAttendancePort.findByUserId(it.userId)
            attendanceService.updateAttendanceToApplication(it.start, it.end!!, it.applicationType, attendanceId!!)
        }.toMutableList()

        saveApplicationPort.saveAll(updateApplications)
        applicationStorySaveAllPort.saveAll(applicationStory)
        saveAttendancePort.saveAll(attendance)
    }

    private fun handleStatusNo(ids: List<UUID>) {
        ids.forEach { id ->
            val application = findApplicationById(id)
            deleteApplicationPort.deleteByIdAndApplicationKind(application.id!!, ApplicationKind.APPLICATION)
        }
    }

    private fun findApplicationById(id: UUID): Application {
        return queryApplicationPort.findByIdAndApplicationKind(id, ApplicationKind.APPLICATION)
            ?: throw ApplicationNotFoundException
    }

    private fun updateApplication(application: Application, adminName: String): Application {
        return application.copy(
            teacherName = adminName,
            status = Status.OK
        )
    }

    private fun createApplicationStory(application: Application): ApplicationStory {
        val startAndEnd = attendanceService.translateApplication(
            application.start,
            application.end!!,
            application.applicationType
        )
        return ApplicationStory(
            reason = application.reason,
            userName = application.userName,
            start = startAndEnd.first(),
            end = startAndEnd.last(),
            date = application.date,
            type = Type.APPLICATION,
            userId = application.userId
        )
    }
}
