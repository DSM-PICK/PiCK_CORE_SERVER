package dsm.pick2024.domain.event.notice

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.presentation.dto.request.Notification
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SendMessageToNoticeEvent(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort
) : SendMessageToNoticeEventPort {

    @Async
    override fun send(content: String) {
        commendTopicSubscriptionPort.sendByTopic(
            Notification(
                topic = Topic.NEW_NOTICE,
                title = "새로운 공지가 등록되었습니다.",
                body = content
            )
        )
    }
}
