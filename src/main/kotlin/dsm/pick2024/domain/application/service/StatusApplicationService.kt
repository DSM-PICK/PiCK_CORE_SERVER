package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.port.out.SaveAllApplicationPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List

@Service
class StatusApplicationService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val findApplicationByIdPort: FindApplicationByIdPort,
    private val deleteApplicationPort: DeleteApplicationPort,
    private val saveAllApplicationPort: SaveAllApplicationPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort
) : StatusApplicationUseCase {

    @Transactional
    override fun statusApplication(status: Status, applicationIds: List<UUID>) {
        val admin = adminFacadeUseCase.currentUser()

        val applicationsUpdate = mutableListOf<Application>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (applicationId in applicationIds) {
            if (Status.NO == status) {
                deleteApplicationPort.deleteById(applicationId)
                continue
            }

            val application = findApplicationByIdPort.findById(applicationId) ?: throw ApplicationNotFoundException

            val updatedApplication = application.copy(
                teacherName = admin.name,
                status = Status.OK,
                applicationStatus = ApplicationStatus.NON_RETURN
            )
            applicationsUpdate.add(updatedApplication)

            val applicationStorySave = ApplicationStory(
                reason = updatedApplication.reason,
                username = updatedApplication.username,
                startTime = updatedApplication.startTime,
                endTime = updatedApplication.endTime,
                date = updatedApplication.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)
        }

        saveAllApplicationPort.saveAll(applicationsUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
