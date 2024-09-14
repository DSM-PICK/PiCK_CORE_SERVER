package dsm.pick2024.domain.event.classroom

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.request.Notification
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class SendMessageToClassroomEvent(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort
) : SendMessageToClassroomEventPot {

    @Async
    override fun send(
        deviceToken: String,
        status: Status,
        classroom: Classroom?,
        isSubscribed: Boolean
    ) {
        println("여까지는 왔다")
        commendTopicSubscriptionPort.sendMessage(
            deviceToken,
            Notification(
                topic = Topic.CLASS_ROOM,
                title = "교실이동 신청이 " +
                    "${if (status == Status.OK) "수락" else "거절"}되었습니다.",
                body = if (status == Status.OK) {
                    "${classroom?.startPeriod}부터 이 가능합동다."
                } else {
                    "신청이 거절되었습니다."
                }
            ),
            isSubscribed
        )
    }
}
