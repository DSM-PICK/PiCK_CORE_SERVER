package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.ApplicationType
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.out.ExistsApplicationPort
import dsm.pick2024.domain.application.port.out.SaveApplicationPort
import dsm.pick2024.domain.earlyreturn.exception.AlreadyApplyingForEarlyReturnException
import dsm.pick2024.domain.earlyreturn.port.`in`.CreateEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.CreateEarlyReturnRequest
import dsm.pick2024.domain.event.dto.UserInfoRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

@Service
class CreateEarlyReturnService(
    private val saveApplicationPort: SaveApplicationPort,
    private val existsApplicationPort: ExistsApplicationPort,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val eventPublisher: ApplicationEventPublisher
) : CreateEarlyReturnUseCase {
    @Transactional
    override fun createEarlyReturn(request: CreateEarlyReturnRequest) {
        val user = userFacadeUseCase.currentUser()

        if (existsApplicationPort.existByUserId(user.id!!)) {
            throw AlreadyApplyingForEarlyReturnException
        }

        saveApplicationPort.save(
            Application(
                userName = user.name,
                reason = request.reason,
                start = request.start,
                status = Status.QUIET,
                date = LocalDate.now(ZoneId.of("Asia/Seoul")),
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                userId = user.id,
                applicationType = ApplicationType.TIME,
                applicationKind = ApplicationKind.EARLY_RETURN
            )
        )
        eventPublisher.publishEvent(UserInfoRequest(this, user.id))
    }
}
