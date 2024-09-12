package dsm.pick2024.domain.application.facade

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ApplicationFacadeUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.domain.service.AttendanceService
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.event.application.SendMessageToApplicationEventPort
import dsm.pick2024.domain.notification.port.out.QueryTopicSubscriptionPort
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationFacade(
    private val queryApplicationPort: QueryApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveAttendancePort: SaveAttendancePort,
    private val queryAttendancePort: QueryAttendancePort,
    private val sendMessageToApplicationEventPort: SendMessageToApplicationEventPort,
    private val queryUserPort: QueryUserPort,
    private val queryTopicSubscriptionPort: QueryTopicSubscriptionPort,
    private val attendanceService: AttendanceService
) : ApplicationFacadeUseCase {

    override fun handleStatusOk(idList: List<UUID>, adminName: String, applicationKind: ApplicationKind) {
        val applicationList = idList.map { id ->
            processApplication(id, adminName, Status.OK, applicationKind)
        }

        saveProcessedApplicationList(applicationList, applicationKind)
    }

    override fun handleStatusNo(idList: List<UUID>, applicationKind: ApplicationKind) {
        idList.forEach { id ->
            val application = findApplicationById(id, applicationKind)
            queryAndSendMessage(application.userId, Status.NO, applicationKind, null)
            deleteApplicationPort.deleteByIdAndApplicationKind(application.id!!, applicationKind)
        }
    }

    private fun processApplication(id: UUID, adminName: String, status: Status, applicationKind: ApplicationKind): Application {
        val application = findApplicationById(id, applicationKind)
        queryAndSendMessage(application.userId, status, applicationKind, application)
        return application.updateApplication(adminName)
    }

    private fun queryAndSendMessage(userId: UUID, status: Status, applicationKind: ApplicationKind, application: Application?) {
        val user = queryUserPort.findByXquareId(userId) ?: throw ApplicationNotFoundException
        val isSubscribed =
            queryTopicSubscriptionPort.queryTopicSubscriptionByDeviceToken(user.deviceToken!!).isSubscribed

        sendMessageToApplicationEventPort.send(
            user.deviceToken,
            status,
            applicationKind,
            application,
            isSubscribed
        )
    }

    private fun saveProcessedApplicationList(applicationList: List<Application>, applicationKind: ApplicationKind) {
        val applicationStoryList = applicationList.map { createApplicationStory(it, applicationKind) }
        val attendanceMutableList = applicationList.map { updateAttendance(it) }.toMutableList()

        saveApplicationPort.saveAll(applicationList)
        applicationStorySaveAllPort.saveAll(applicationStoryList)
        saveAttendancePort.saveAll(attendanceMutableList)
    }

    private fun updateAttendance(application: Application): Attendance {
        val attendanceId = queryAttendancePort.findByUserId(application.userId)
        return attendanceService.updateAttendanceToApplication(
            application.start,
            application.end!!,
            application.applicationType,
            attendanceId!!
        )
    }

    private fun findApplicationById(id: UUID, applicationKind: ApplicationKind): Application {
        return queryApplicationPort.findByIdAndApplicationKind(id, applicationKind)
            ?: throw ApplicationNotFoundException
    }

    private fun Application.updateApplication(adminName: String): Application {
        return copy(teacherName = adminName, status = Status.OK)
    }

    private fun createApplicationStory(application: Application, applicationKind: ApplicationKind): ApplicationStory {
        val (start, end) = if (applicationKind == ApplicationKind.APPLICATION) {
            attendanceService.translateApplication(application.start, application.end!!, application.applicationType)
        } else {
            attendanceService.translateApplication(application.start, null, ApplicationType.TIME)
        }

        return ApplicationStory(
            reason = application.reason,
            userName = application.userName,
            start = start,
            end = end,
            date = application.date,
            type = if (applicationKind == ApplicationKind.APPLICATION) Type.APPLICATION else Type.EARLY_RETURN,
            userId = application.userId
        )
    }
}
