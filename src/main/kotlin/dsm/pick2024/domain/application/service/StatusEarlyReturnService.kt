package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.port.`in`.StatusEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.DeleteEarlyReturnApplicationPort
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.application.port.out.SaveEarlyReturnPort
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class StatusEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveEarlyReturnPort: SaveEarlyReturnPort,
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val deleteEarlyReturnApplicationPort: DeleteEarlyReturnApplicationPort,
    private val applicationStorySavePort: ApplicationStorySavePort
) : StatusEarlyReturnUseCase {

    @Transactional
    override fun statusEarlyReturn(status: Status, earlyReturnIds: List<UUID>) {
        val admin = adminFacadeUseCase.currentUser()

        val earlyReturnsUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (earlyReturnId in earlyReturnIds) {
            if (Status.NO == status) {
                deleteEarlyReturnApplicationPort.deleteById(earlyReturnId)
                continue
            }

            val earlyReturn = findEarlyReturnByIdPort.findById(earlyReturnId)
                ?: throw EarlyReturnApplicationNotFoundException

            val updateEarlyReturn = earlyReturn.copy(
                teacherName = admin.name,
                status = Status.OK
            )
            earlyReturnsUpdate.add(updateEarlyReturn)

            val applicationStorySave = ApplicationStory(
                reason = earlyReturn.reason,
                username = earlyReturn.username,
                startTime = earlyReturn.startTime,
                date = earlyReturn.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)
        }

        saveEarlyReturnPort.saveAll(earlyReturnsUpdate)
        applicationStorySavePort.saveAll(applicationStory)
    }
}
