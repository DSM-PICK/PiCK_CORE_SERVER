package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.StatusEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.earlyreturn.port.out.SaveAllEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StatusEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveAllEarlyReturnPort: SaveAllEarlyReturnPort,
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort,
    private val deleteEarlyReturnByIdPort: FindEarlyReturnByIdPort
) : StatusEarlyReturnUseCase {
    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        val earlyReturnUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        if (request.status == Status.NO) {
            for (id in request.ids) {
                findEarlyReturnByIdPort.findById(id)
                    ?: throw EarlyReturnApplicationNotFoundException
                deleteEarlyReturnByIdPort.findById(id)
            }
        }

        for (earlyReturnId in request.ids) {
            val earlyReturn =
                findEarlyReturnByIdPort.findById(earlyReturnId)
                    ?: throw EarlyReturnApplicationNotFoundException

            val updateEarlyReturn =
                earlyReturn.copy(
                    teacherName = admin.name,
                    status = Status.OK
                )
            earlyReturnUpdate.add(updateEarlyReturn)

            val applicationStorySave =
                ApplicationStory(
                    reason = earlyReturn.reason,
                    userName = earlyReturn.userName,
                    startTime = earlyReturn.startTime,
                    date = earlyReturn.date,
                    type = Type.EARLY_RETURN,
                    userId = updateEarlyReturn.userId
                )
            applicationStory.add(applicationStorySave)
        }

        saveAllEarlyReturnPort.saveAll(earlyReturnUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
