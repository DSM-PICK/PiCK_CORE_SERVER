package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.entity.WeekendMealJpaEntity
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.persistence.WeekendMealPersistenceAdapter
import dsm.pick2024.domain.weekendmeal.port.`in`.SaveAllWeekendMealUserUseCase
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
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
                WeekendMealJpaEntity(
                    id = null,
                    userId = user.id,
                    username = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    status = Status.QUIET
                )
            }.toMutableList()
        weekendMealPersistenceAdapter.saveAll(weekendMealEntities)
    }
}
