package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StatusApplicationService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val findApplicationByIdPort: FindApplicationByIdPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySavePort: ApplicationStorySavePort
) : StatusApplicationUseCase {

    @Transactional
    override fun statusApplication(status: Status, applicationId: UUID) {
        val admin = adminFacadeUseCase.currentUser()

        if (Status.NO == status) {
            deleteApplicationPort.deleteById(applicationId)
        }

        val application = findApplicationByIdPort.findById(applicationId) ?: throw ApplicationNotFoundException

        val update = application.copy(
            teacherName = admin.name,
            status = Status.OK,
            applicationStatus = ApplicationStatus.NON_RETURN
        )
        saveApplicationPort.save(update)

        applicationStorySavePort.save(
            ApplicationStory(
                reason = application.reason,
                username = application.username,
                startTime = application.startTime,
                endTime = application.endTime,
                date = application.date,
                type = Type.APPLICATION
            )
        )
    }
}
