package dsm.pick2024.domain.notification.port.out

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription

interface CommendTopicSubscriptionPort {
    fun sendMessage(
        token: String,
        topicSubscription: TopicSubscription
    )

    fun sendByTopic(
        topic: Topic,
        title: String,
        body: String
    )

    fun subscribeTopic(token: String, topic: Topic)


    fun unsubscribeTopic(token: String, topic: Topic)
}
