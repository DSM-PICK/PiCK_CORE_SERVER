package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.devicetoken.port.out.QueryUserDeviceTokenPort
import dsm.pick2024.domain.outbox.port.`in`.OutboxFacadeUseCase
import dsm.pick2024.domain.user.port.out.QueryUserPort
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.NotificationWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.port.out.WeekendMealPeriodPort
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class NotificationWeekendMealService(
    private val queryUserPort: QueryUserPort,
    private val weekendMealPeriodPort: WeekendMealPeriodPort,
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase,
    private val outboxFacadeUseCase: OutboxFacadeUseCase,
    private val queryUserDeviceTokenPort: QueryUserDeviceTokenPort
) : NotificationWeekendMealUseCase {
    companion object {
        private const val OK = "신청하셨습니다."
        private const val NO = "신청하지 않았습니다."
    }

    override fun execute() {
        val weekendMealPeriod = weekendMealPeriodPort.queryWeekendPeriodByDate(LocalDate.now())
        if (weekendMealPeriod != null) {
            val users = queryUserPort.findAll()
            val today = LocalDate.now()

            when (today) {
                weekendMealPeriod.end -> {
                    users.forEach { user ->
                        val status = weekendMealFinderUseCase.findByUserIdOrThrow(user.id).status

                        // 2. 해당 학생의 모든 기기 토큰 조회
                        val tokens = queryUserDeviceTokenPort.findAllByUserId(user.id)
                            .map { it.deviceToken }

                        tokens.forEach { token ->
                            outboxFacadeUseCase.sendNotification(
                                deviceToken = token,
                                title = "[PiCK] 급식 신청 기간 알림",
                                body = "신청 기간: ${weekendMealPeriod.start} ~ ${weekendMealPeriod.end}" +
                                    " 현재 ${user.name}님은 ${weekendMealPeriod.month.ordinal}월 주말급식을" +
                                    " ${if (status == Status.OK) OK else NO}"
                            )
                        }
                    }
                }

                weekendMealPeriod.start -> {
                    users.forEach { user ->
                        val tokens = queryUserDeviceTokenPort.findAllByUserId(user.id)
                            .map { it.deviceToken }

                        tokens.forEach { token ->
                            outboxFacadeUseCase.sendNotification(
                                deviceToken = token,
                                title = "[PiCK] 급식 신청 기간 알림",
                                body = "신청 기간: ${weekendMealPeriod.start} ~ ${weekendMealPeriod.end}"
                            )
                        }
                    }
                }
            }
        }
    }
}
