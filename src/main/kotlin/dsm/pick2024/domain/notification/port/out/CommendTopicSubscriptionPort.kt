package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.presentation.dto.request.Notification

interface CommendTopicSubscriptionPort {
    fun sendMessage(
        token: String,
        notification: Notification,
        isSubscribed: Boolean
    )

    fun sendByTopic(
        notification: Notification
    )

    fun subscribeTopic(token: String, topic: Topic)

    fun unsubscribeTopic(token: String, topic: Topic)
}
