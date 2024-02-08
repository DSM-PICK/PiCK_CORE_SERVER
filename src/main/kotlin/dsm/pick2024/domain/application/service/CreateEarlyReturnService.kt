package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.application.domain.EarlyReturn
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.AlreadyApplyingForEarlyReturnException
import dsm.pick2024.domain.application.port.`in`.CreateEarlyReturnUseCase
import dsm.pick2024.domain.application.port.out.ExistsEarlyReturnByUsernamePort
import dsm.pick2024.domain.application.port.out.SaveEarlyReturnPort
import dsm.pick2024.domain.application.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CreateEarlyReturnService(
    private val saveEarlyReturnPort: SaveEarlyReturnPort,
    private val existsEarlyReturnByUsernamePort: ExistsEarlyReturnByUsernamePort,
    private val userFacadeUseCase: UserFacadeUseCase
) : CreateEarlyReturnUseCase {

    @Transactional
    override fun createEarlyReturn(request: CreateEarlyReturnRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsEarlyReturnByUsernamePort.existsByUsername(user.name) == true) {
            throw AlreadyApplyingForEarlyReturnException
        }

        saveEarlyReturnPort.save(
            EarlyReturn(
                username = user.name,
                reason = request.reason,
                startTime = request.startTime,
                status = Status.QUIET,
                date = LocalDate.now()
            )
        )
    }
}
