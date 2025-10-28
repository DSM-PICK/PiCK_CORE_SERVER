package dsm.pick2024.domain.earlyreturn.service

import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.earlyreturn.port.`in`.QueryFloorEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.presentation.dto.response.QueryEarlyReturnResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryFloorEarlyReturnService(
    private val applicationFinderUseCase: ApplicationFinderUseCase
) : QueryFloorEarlyReturnUseCase {

    @Transactional(readOnly = true)
    override fun queryFloorEarlyReturn(floor: Int) =

        applicationFinderUseCase.findByFloorAndApplicationKindOrThrow(floor, ApplicationKind.EARLY_RETURN)
            .filter { it.status == Status.QUIET }
            .map {
                QueryEarlyReturnResponse(it)
            }
}
