package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusOKApplicationUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.port.out.SaveAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import dsm.pick2024.infrastructure.zxing.service.GenerateApplicationQRCodeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusOKApplicationService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val findApplicationByIdPort: FindApplicationByIdPort,
    private val saveAllApplicationPort: SaveAllApplicationPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort,
    private val qrCodeUseCase: GenerateApplicationQRCodeUseCase,
) : StatusOKApplicationUseCase {

    @Transactional
    override fun statusOKApplication(request: StatusApplicationRequest?) {
        val admin = adminFacadeUseCase.currentUser()

        val applicationUpdate = mutableListOf<Application>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (id in request!!.ids) {
            val application = findApplicationByIdPort.findById(id) ?: throw ApplicationNotFoundException

            val updatedApplication = application.copy(
                teacherName = admin.name,
                status = Status.OK,
                applicationStatus = ApplicationStatus.NON_RETURN
            )
            applicationUpdate.add(updatedApplication)

            val applicationStorySave = ApplicationStory(
                reason = updatedApplication.reason,
                username = updatedApplication.username,
                startTime = updatedApplication.startTime,
                endTime = updatedApplication.endTime,
                date = updatedApplication.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)

            qrCodeUseCase.generateApplicationQRCode(
                application.username,
                application.teacherName!!,
                application.startTime,
                application.endTime,
                application.reason
            )
        }

        saveAllApplicationPort.saveAll(applicationUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
