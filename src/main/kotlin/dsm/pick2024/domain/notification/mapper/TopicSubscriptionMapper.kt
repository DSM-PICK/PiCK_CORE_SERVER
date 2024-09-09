package dsm.pick2024.domain.notification.mapper

import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.entity.TopicSubscriptionJpaEntity
import org.springframework.stereotype.Component

@Component
class TopicSubscriptionMapper {
    fun toEntity(domain: TopicSubscription) =
        domain.run {
            TopicSubscriptionJpaEntity(
                id = id,
                topic = topic,
                isSubscribed = isSubscribed,
                deviceToken = deviceToken,
                userId = userId
            )
        }

    fun toDomain(entity: TopicSubscriptionJpaEntity) =
        entity.run {
            TopicSubscription(
                id = id,
                topic = topic,
                isSubscribed = isSubscribed,
                deviceToken = deviceToken,
                userId = userId
            )
        }
}
