package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.Notification

interface CommendNotificationPort {
    fun sendMessage(
        token: String,
        notification: Notification
    )

    fun sendByTopic(
        notification: Notification
    )

    fun subscribeTopic(token: String, topic: Topic)


    fun unsubscribeTopic(token: String, topic: Topic)
}
