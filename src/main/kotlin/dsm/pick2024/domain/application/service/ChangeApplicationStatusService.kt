package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.exception.AlreadyApplyingForPicnicException
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.`in`.ChangeApplicationStatusUseCase
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import dsm.pick2024.domain.application.service.processor.ApplicationApprovalProcessor
import dsm.pick2024.domain.application.service.processor.ApplicationRejectionProcessor
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeApplicationStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val userFacadeUseCase: UserFacadeUseCase,
    private val applicationFinderUseCase: ApplicationFinderUseCase,
    private val approvalProcessor: ApplicationApprovalProcessor,
    private val rejectionProcessor: ApplicationRejectionProcessor
) : ChangeApplicationStatusUseCase {

    @Transactional
    override fun changeStatusApplication(request: ApplicationStatusRequest) {
        val admin = adminFacadeUseCase.currentAdmin()
        val applications = request.idList.map { applicationFinderUseCase.findByIdOrThrow(it) }
        applications.forEach {
            if (it.applicationKind != ApplicationKind.APPLICATION) {
                throw ApplicationNotFoundException
            }
        }
        applications.forEach { if (it.status != Status.QUIET) throw AlreadyApplyingForPicnicException }

        val deviceTokens = applications.mapNotNull {
            userFacadeUseCase.getUserById(
                it.userId
            ).deviceToken
        }

        if (request.status == Status.OK) {
            approvalProcessor.process(applications, admin.name, deviceTokens)
        } else {
            rejectionProcessor.process(applications, admin.name, deviceTokens)
        }
    }
}
