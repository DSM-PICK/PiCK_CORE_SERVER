package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription

interface QueryTopicSubscriptionPort {
    fun queryNotificationByDeviceTokenAndTopic(deviceToken: String, topic: Topic): TopicSubscription?

    fun queryAllNotificationByDeviceToken(deviceToken: String): List<TopicSubscription>?
}
