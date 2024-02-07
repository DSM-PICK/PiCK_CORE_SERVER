package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.EarlyFacadeUseCase
import dsm.pick2024.domain.application.port.`in`.OKEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.FindEarlyReturnByUsernamePort
import dsm.pick2024.domain.application.port.out.SaveEarlyReturnPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OKEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveEarlyReturnPort: SaveEarlyReturnPort,
    private val earlyFacadeUseCase: EarlyFacadeUseCase
): OKEarlyReturnUseCase {

    @Transactional
    override fun okEarlyReturn(status: Status, name: String) {
        val admin = adminFacadeUseCase.currentUser()
        val earlyReturn = earlyFacadeUseCase.getUsername(name)

        val updateEarlyReturn = earlyReturn.copy(
            teacherName = admin.name,
            status = Status.OK
        )

        saveEarlyReturnPort.save(updateEarlyReturn)
    }
}
