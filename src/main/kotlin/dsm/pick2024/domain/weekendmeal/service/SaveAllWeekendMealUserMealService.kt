package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.persistence.WeekendMealPersistenceAdapter
import dsm.pick2024.domain.weekendmeal.port.`in`.SaveAllWeekendMealUserUseCase
import dsm.pick2024.infrastructure.feign.xquare.client.XquareFeignClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAllWeekendMealUserMealService(
    private val xquareFeignClient: XquareFeignClient,
    private val weekendMealPersistenceAdapter: WeekendMealPersistenceAdapter
) : SaveAllWeekendMealUserUseCase {

    @Transactional
    override fun saveAll(key: String) {
        val xquareWeekendMealUserInfo = xquareFeignClient.userAll(key)
        val weekendMealEntities =
            xquareWeekendMealUserInfo.map { user ->
                WeekendMeal(
                    userId = user.id,
                    userName = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    status = Status.NO
                )
            }.toMutableList()
        weekendMealPersistenceAdapter.saveAll(weekendMealEntities)
    }
}
