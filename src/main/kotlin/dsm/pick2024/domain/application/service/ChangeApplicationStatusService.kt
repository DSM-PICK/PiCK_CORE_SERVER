package dsm.pick2024.domain.application.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.facade.ApplicationFacade
import dsm.pick2024.domain.application.port.`in`.ChangeApplicationStatusUseCase
import dsm.pick2024.domain.application.presentation.dto.request.ApplicationStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeApplicationStatusService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val applicationFacade: ApplicationFacade
) : ChangeApplicationStatusUseCase {

    @Transactional
    override fun changeStatusApplication(request: ApplicationStatusRequest) {
        val admin = adminFacadeUseCase.currentAdmin()
        if (request.status == Status.OK) {
            applicationFacade.handleStatusOk(request.idList, admin.name, ApplicationKind.APPLICATION)
        } else {
            applicationFacade.handleStatusNo(request.idList, ApplicationKind.APPLICATION)
        }
    }
}
