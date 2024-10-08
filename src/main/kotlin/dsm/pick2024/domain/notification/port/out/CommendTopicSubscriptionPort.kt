package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.enums.Topic
import dsm.pick2024.domain.notification.presentation.dto.request.Notification

interface CommendTopicSubscriptionPort {
    fun sendMessage(
        token: List<String>,
        notification: Notification
    )

    fun sendByTopic(
        notification: Notification
    )

    fun subscribeTopic(token: String, topic: Topic)

    fun unsubscribeTopic(token: String, topic: Topic)
}
