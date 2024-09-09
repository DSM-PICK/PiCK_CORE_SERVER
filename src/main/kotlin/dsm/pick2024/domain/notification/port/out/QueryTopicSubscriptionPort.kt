package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import java.util.UUID

interface QueryTopicSubscriptionPort {
    fun queryTopicSubscriptionByDeviceTokenAndTopic(deviceToken: String, topic: Topic): TopicSubscription?

    fun queryAllTopicSubscriptionByUserId(userId: String): List<TopicSubscription>?
}
