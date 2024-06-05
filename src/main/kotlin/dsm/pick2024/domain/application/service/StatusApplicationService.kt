package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusApplicationUseCase
import dsm.pick2024.domain.application.port.out.DeleteApplicationPort
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusApplicationService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val queryApplicationPort: QueryApplicationPort,
    private val saveApplicationPort: SaveApplicationPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteApplicationPort: DeleteApplicationPort
) : StatusApplicationUseCase {

    @Transactional
    override fun statusApplication(request: ApplicationStatusRequest?) {
        val admin = adminFacadeUseCase.currentAdmin()

        val applicationUpdate = mutableListOf<Application>()
        val applicationStory = mutableListOf<ApplicationStory>()

        if (request!!.status == Status.NO) {
            for (id in request.ids) {
                val application = queryApplicationPort.findById(id) ?: throw ApplicationNotFoundException
                deleteApplicationPort.deleteById(application.id!!)
            }
            return
        }

        for (id in request.ids) {
            val application = queryApplicationPort.findById(id) ?: throw ApplicationNotFoundException

            val updatedApplication =
                application.copy(
                    teacherName = admin.name,
                    status = Status.OK,
                    applicationStatus = ApplicationStatus.NON_RETURN
                )
            applicationUpdate.add(updatedApplication)

            val applicationStorySave =
                ApplicationStory(
                    reason = updatedApplication.reason,
                    userName = updatedApplication.userName,
                    startTime = updatedApplication.startTime,
                    endTime = updatedApplication.endTime,
                    date = updatedApplication.date,
                    type = Type.APPLICATION,
                    userId = updatedApplication.userId
                )
            applicationStory.add(applicationStorySave)
        }

        saveApplicationPort.saveAll(applicationUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
