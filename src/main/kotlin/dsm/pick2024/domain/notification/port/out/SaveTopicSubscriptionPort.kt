package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.notification.domain.TopicSubscription

interface SaveTopicSubscriptionPort {
    fun save(topicSubscription: TopicSubscription)
}
