package dsm.pick2024.domain.notification.persistence

import dsm.pick2024.domain.event.enums.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.mapper.TopicSubscriptionMapper
import dsm.pick2024.domain.notification.persistence.repository.TopicSubscriptionRepository
import dsm.pick2024.domain.notification.port.out.TopicSubscriptionPort
import org.springframework.stereotype.Component

@Component
class TopicSubscriptionPersistenceAdapter(
    private val topicSubscriptionRepository: TopicSubscriptionRepository,
    private val topicSubscriptionMapper: TopicSubscriptionMapper
) : TopicSubscriptionPort {

    override fun save(topicSubscription: TopicSubscription) {
        topicSubscriptionRepository.save(topicSubscriptionMapper.toEntity(topicSubscription))
    }

    override fun queryTopicSubscriptionByDeviceTokenAndTopic(deviceToken: String, topic: Topic): TopicSubscription? {
        return topicSubscriptionRepository.findByDeviceTokenAndTopic(deviceToken, topic)
            .let { topicSubscriptionMapper.toDomain(it) }
    }

    override fun queryAllTopicSubscriptionByUserId(userId: String): List<TopicSubscription>? {
        return topicSubscriptionRepository.findAllByUserId(userId)
            .map { topicSubscriptionMapper.toDomain(it) }
    }
}
