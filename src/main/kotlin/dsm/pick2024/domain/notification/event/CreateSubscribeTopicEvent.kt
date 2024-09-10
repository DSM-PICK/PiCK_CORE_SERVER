package dsm.pick2024.domain.notification.event

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.out.QueryTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.SaveTopicSubscriptionPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CreateSubscribeTopicEvent(
    private val saveTopicSubscriptionPort: SaveTopicSubscriptionPort,
    private val queryTopicSubscriptionPort: QueryTopicSubscriptionPort
) : CreateSubscribeTopicEventPort {

    @Transactional
    override fun execute(deviceToken: String, userId: String) {
        val topicSubscription = queryTopicSubscriptionPort.queryAllTopicSubscriptionByUserId(userId)
        if (topicSubscription.isNullOrEmpty()) {
            val topics = listOf(
                Topic.NEW_NOTICE,
                Topic.APPLICATION,
                Topic.EARLY_RETURN,
                Topic.WEEKEND_MEAL
            )
            topics.forEach { it ->
                saveTopicSubscriptionPort.save(
                    TopicSubscription(
                        deviceToken = deviceToken,
                        isSubscribed = false,
                        topic = it,
                        userId = userId
                    )
                )
            }
        }
        topicSubscription?.map {
            saveTopicSubscriptionPort.save(
                it.copy(
                    deviceToken = deviceToken
                )
            )
        }
    }
}
