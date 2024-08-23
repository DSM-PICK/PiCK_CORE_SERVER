package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.SaveAllApplicationStoryPort
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.ChangeEarlyReturnStatusUseCase
import dsm.pick2024.domain.earlyreturn.port.out.DeleteEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.port.out.QueryEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.port.out.SaveEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeEarlyReturnStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveEarlyReturnPort: SaveEarlyReturnPort,
    private val queryEarlyReturnPort: QueryEarlyReturnPort,
    private val applicationStorySaveAllPort: SaveAllApplicationStoryPort,
    private val deleteEarlyReturnPort: DeleteEarlyReturnPort
) : ChangeEarlyReturnStatusUseCase {
    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        val earlyReturnUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        if (request.status == Status.NO) {
            for (id in request.ids) {
                queryEarlyReturnPort.findById(id)
                    ?: throw EarlyReturnApplicationNotFoundException
                deleteEarlyReturnPort.deleteByUserId(id)
            }
            return
        }

        for (earlyReturnId in request.ids) {
            val earlyReturn =
                queryEarlyReturnPort.findById(earlyReturnId)
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

        saveEarlyReturnPort.saveAll(earlyReturnUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }
}
