package dsm.pick2024.domain.meal.service

import dsm.pick2024.domain.meal.domain.Meal
import dsm.pick2024.domain.meal.port.`in`.RetryMealUseCase
import dsm.pick2024.domain.meal.port.out.MealPort
import dsm.pick2024.global.common.TimeUtils
import dsm.pick2024.infrastructure.feign.neis.NeisMealFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.TransactionTemplate
import java.time.YearMonth

@Service
class RetryMealService(
    private val mealPort: MealPort,
    private val neisMealFeignClientService: NeisMealFeignClientService,
    transactionManager: PlatformTransactionManager
) : RetryMealUseCase {

    private val transactionTemplate = TransactionTemplate(transactionManager)

    override fun execute() {
        val currentMonth = YearMonth.from(TimeUtils.nowLocalDate())

        // 트랜잭션 외부에서 Feign 호출
        val mealsToSave = neisMealFeignClientService.getNeisInfoToEntityByYearMonth(currentMonth)
            .groupBy { it.mealDate }
            .flatMap { (date, mealInfos) ->
                mealInfos.groupBy { it.mealType }
                    .mapNotNull { (mealType, mealsOfType) ->
                        val menu = mealsOfType.mapNotNull { it.menu }.distinct().joinToString("||")
                        val cal = mealsOfType.map { it.cal }.get(0)
                        if (menu.isNotEmpty()) {
                            Meal(mealDate = date, mealType = mealType, menu = menu, cal = cal)
                        } else {
                            null
                        }
                    }
            }

        // Feign 응답 수신 후 트랜잭션 시작 → 삭제 + 저장
        transactionTemplate.execute {
            mealPort.deleteByYearMonth(currentMonth)
            mealsToSave.forEach { mealPort.save(it) }
        }
    }
}
