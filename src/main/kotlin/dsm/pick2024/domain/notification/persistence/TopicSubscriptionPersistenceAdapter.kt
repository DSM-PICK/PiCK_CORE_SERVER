package dsm.pick2024.domain.notification.persistence

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.mapper.TopicSubscriptionMapper
import dsm.pick2024.domain.notification.persistence.repository.TopicSubscriptionRepository
import dsm.pick2024.domain.notification.port.out.TopicSubscriptionPort
import org.springframework.stereotype.Component

@Component
class TopicSubscriptionPersistenceAdapter(
    private val topicSubscriptionRepository: TopicSubscriptionRepository,
    private val topicSubscriptionMapper: TopicSubscriptionMapper
): TopicSubscriptionPort {

    override fun save(topicSubscription: TopicSubscription) {
        topicSubscriptionRepository.save(topicSubscriptionMapper.toEntity(topicSubscription))
    }

    override fun queryNotificationByDeviceTokenAndTopic(deviceToken: String, topic: Topic): TopicSubscription? {
        return topicSubscriptionRepository.findByDeviceTokenAndTopic(deviceToken, topic)
            .let { topicSubscriptionMapper.toDomain(it) }
    }

    override fun queryAllNotificationByDeviceToken(
        deviceToken: String,
    ): List<TopicSubscription>? {
        return topicSubscriptionRepository.findAllByDeviceToken(deviceToken)
            .map { topicSubscriptionMapper.toDomain(it) }
    }
}
