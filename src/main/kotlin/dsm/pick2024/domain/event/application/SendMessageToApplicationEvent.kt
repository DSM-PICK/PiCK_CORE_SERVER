package dsm.pick2024.domain.event.application

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationKind
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.request.Notification
import org.springframework.stereotype.Component

@Component
class SendMessageToApplicationEvent(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort
) : SendMessageToApplicationEventPort {

    override fun send(
        deviceToken: String,
        status: Status,
        applicationKind: ApplicationKind,
        application: Application?,
        isSubscribed: Boolean
    ) {
        commendTopicSubscriptionPort.sendMessage(
            deviceToken,
            Notification(
                topic = Topic.APPLICATION,
                title = "${if (applicationKind.name == ApplicationKind.APPLICATION.name) "외출" else "조기귀가"} 신청이 " +
                    "${if (status == Status.OK) "수락" else "거절"}되었습니다.",
                body = when (status) {
                    Status.OK -> "${application?.start}부터 이용 가능합니다."
                    else -> "신청이 거절되었습니다."
                }
            ),
            isSubscribed
        )
    }
}
