package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.user.facade.UserFacade
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryMyWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindWeekendMealByUserIdPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryStatusResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QueryMyWeekendMealStatusService(
    private val userFacade: UserFacade,
    private val findWeekendMealByUserIdPort: FindWeekendMealByUserIdPort
) : QueryMyWeekendMealStatusUseCase {

    @Transactional(readOnly = true)
    override fun queryMyWeekendMealStatus(): QueryStatusResponse {
        val weekend = findWeekendMealByUserIdPort.findByUserId(userFacade.currentUser().xquareId)
        return QueryStatusResponse(weekend!!.status)
    }
}
