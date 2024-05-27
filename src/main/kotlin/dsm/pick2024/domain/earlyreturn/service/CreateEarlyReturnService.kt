package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.AlreadyApplyingForEarlyReturnException
import dsm.pick2024.domain.earlyreturn.port.`in`.CreateEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.ExistsEarlyReturnByUserIdPort
import dsm.pick2024.domain.earlyreturn.port.out.SaveEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class CreateEarlyReturnService(
    private val saveEarlyReturnPort: SaveEarlyReturnPort,
    private val existsEarlyReturnByUserIdPort: ExistsEarlyReturnByUserIdPort,
    private val userFacadeUseCase: UserFacadeUseCase
) : CreateEarlyReturnUseCase {
    @Transactional
    override fun createEarlyReturn(request: CreateEarlyReturnRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsEarlyReturnByUserIdPort.existsByUserId(user.id)) {
            throw AlreadyApplyingForEarlyReturnException
        }

        saveEarlyReturnPort.save(
            EarlyReturn(
                username = user.name,
                reason = request.reason,
                startTime = request.startTime,
                status = Status.QUIET,
                date = LocalDate.now(ZoneId.of("Asia/Seoul")),
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                userId = user.id
            )
        )
    }
}
