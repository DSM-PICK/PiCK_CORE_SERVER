package dsm.pick2024.domain.event.weekendmeal

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.request.Notification
import org.springframework.stereotype.Component

@Component
class SendMessageToWeekendMealEvent(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort
): SendMessageToWeekendMealEventPort {

    override fun send() {
        commendTopicSubscriptionPort.sendByTopic(
            Notification(
                topic = Topic.WEEKEND_MEAL,
                title = "주말 급식 신청이 시작되었습니다.",
                body = "신청 마감일까지 3일 남았습니다. 서둘러 신청해주세요!"
            )
        )
    }

}
