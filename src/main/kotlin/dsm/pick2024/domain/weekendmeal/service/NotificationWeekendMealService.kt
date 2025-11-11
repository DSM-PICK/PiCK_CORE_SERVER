package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.fcm.port.out.FcmSendPort
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
    private val fcmSendPort: FcmSendPort,
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase
) : NotificationWeekendMealUseCase {
    companion object {
        private const val OK = "신청하셨습니다."
        private const val NO = "신청하지 않았습니다."
    }

    override fun execute() {
        val weekendMealPeriod = weekendMealPeriodPort.queryWeekendPeriodByDate(LocalDate.now())
        if (weekendMealPeriod != null) {
            val users = queryUserPort.findAll()
            when (LocalDate.now()) {
                weekendMealPeriod.end -> {
                    users.map {
                        val status = weekendMealFinderUseCase.findByUserIdOrThrow(it.id).status
                        if (it.deviceToken != null) {
                            fcmSendPort.send(
                                deviceToken = it.deviceToken,
                                title = "[PiCK] 급식 신청 기간 알림",
                                body = "신청 기간: ${weekendMealPeriod.start} ~ ${weekendMealPeriod.end}" +
                                    " 현재 ${it.name}님은 ${weekendMealPeriod.month.ordinal}월 주말급식을" +
                                    " ${ when (status){
                                        Status.OK -> OK
                                        Status.NO -> NO
                                    }}"
                            )
                        }
                    }
                }
                weekendMealPeriod.start -> {
                    users.map {
                        if (it.deviceToken != null) {
                            fcmSendPort.send(
                                deviceToken = it.deviceToken,
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
