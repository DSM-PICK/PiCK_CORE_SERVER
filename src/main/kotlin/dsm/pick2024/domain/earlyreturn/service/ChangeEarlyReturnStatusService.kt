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
    private val deleteEarlyReturnPort: DeleteEarlyReturnPort,
    
) : ChangeEarlyReturnStatusUseCase {

    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()

        if (request.status == Status.NO) {
<<<<<<< Updated upstream
            for (id in request.ids) {
                queryEarlyReturnPort.findByUserId(id)
                    ?: throw EarlyReturnApplicationNotFoundException
                deleteEarlyReturnPort.deleteByUserId(id)
            }
            return
        }

        for (earlyReturnId in request.ids) {
            val earlyReturn =
                queryEarlyReturnPort.findByUserId(earlyReturnId)
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
=======
            handleRejection(request.ids)
        } else {
            handleApproval(request.ids, admin.name)
        }
    }

    private fun handleRejection(ids: List<String>) {
        ids.forEach { id ->
            queryEarlyReturnPort.findByUserId(id)
                ?: throw EarlyReturnApplicationNotFoundException
            deleteEarlyReturnPort.deleteByUserId(id)
        }
    }

    private fun handleApproval(ids: List<String>, teacherName: String) {
        val earlyReturns = ids.mapNotNull { id ->
            queryEarlyReturnPort.findByUserId(id)?.copy(
                teacherName = teacherName,
                status = Status.OK
            )
        }

        if (earlyReturns.isEmpty()) {
            throw EarlyReturnApplicationNotFoundException
>>>>>>> Stashed changes
        }

        val applicationStories = earlyReturns.map { earlyReturn ->
            createApplicationStoryFromEarlyReturn(earlyReturn)
        }

        saveEarlyReturnPort.saveAll(earlyReturns)
        applicationStorySaveAllPort.saveAll(applicationStories)
    }

    private fun createApplicationStoryFromEarlyReturn(earlyReturn: EarlyReturn): ApplicationStory {
        return ApplicationStory(
            reason = earlyReturn.reason,
            userName = earlyReturn.userName,
            start = earlyReturn.,
            date = earlyReturn.date,
            type = Type.EARLY_RETURN,
            userId = earlyReturn.userId
        )
    }
}
