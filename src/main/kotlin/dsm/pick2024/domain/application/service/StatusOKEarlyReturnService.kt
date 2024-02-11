package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.port.`in`.StatusOKEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.application.port.out.SaveAllEarlyReturnPort
import dsm.pick2024.domain.application.presentation.dto.request.StatusEarlyReturnRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import dsm.pick2024.infrastructure.zxing.service.GenerateApplicationQRCodeUseCase
import dsm.pick2024.infrastructure.zxing.service.GenerateEarlyReturnQRCodeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusOKEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveAllEarlyReturnPort: SaveAllEarlyReturnPort,
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort,
    private val qrCodeUseCase: GenerateEarlyReturnQRCodeUseCase,
) : StatusOKEarlyReturnUseCase {

    @Transactional
    override fun statusOKEarlyReturn(request: StatusEarlyReturnRequest?) {
        val admin = adminFacadeUseCase.currentUser()

        val earlyReturnUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (earlyReturnId in request!!.earlyReturnIds) {
            val earlyReturn = findEarlyReturnByIdPort.findById(earlyReturnId)
                ?: throw EarlyReturnApplicationNotFoundException

            val updateEarlyReturn = earlyReturn.copy(
                teacherName = admin.name,
                status = Status.OK
            )
            earlyReturnUpdate.add(updateEarlyReturn)

            val applicationStorySave = ApplicationStory(
                reason = earlyReturn.reason,
                username = earlyReturn.username,
                startTime = earlyReturn.startTime,
                date = earlyReturn.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)
            qrCodeUseCase.generateEarlyReturnQRCode(
                earlyReturn.username,
                earlyReturn.teacherName!!,
                earlyReturn.startTime,
                earlyReturn.reason
            )
        }

        saveAllEarlyReturnPort.saveAll(earlyReturnUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
