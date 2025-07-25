package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.AlreadyApplyingForPicnicException
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.ChangeEarlyReturnStatusUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import dsm.pick2024.domain.earlyreturn.service.processor.EarlyReturnApprovalProcessor
import dsm.pick2024.domain.earlyreturn.service.processor.EarlyReturnRejectionProcessor
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ChangeEarlyReturnStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val earlyReturnApprovalProcessor: EarlyReturnApprovalProcessor,
    private val earlyReturnRejectionProcessor: EarlyReturnRejectionProcessor
) : ChangeEarlyReturnStatusUseCase {

    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentAdmin()
        val applications = request.idList.map { findApplicationById(it) }
        applications.forEach { a -> if (a.status != Status.QUIET) throw AlreadyApplyingForPicnicException }

        val deviceTokens = applications.mapNotNull {
            userFacadeUseCase.getUserById(
                it.userId
            ).deviceToken
        }.filter { it.isNotBlank() }

        if (request.status == Status.OK) {
            earlyReturnApprovalProcessor.process(applications, admin.name, deviceTokens)
        } else {
            earlyReturnRejectionProcessor.process(applications, admin.name, deviceTokens)
        }
    }
    private fun findApplicationById(id: UUID): Application {
        return applicationFinderUseCase.findByIdOrThrow(id)
    }
}
