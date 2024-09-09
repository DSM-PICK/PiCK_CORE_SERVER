package dsm.pick2024.domain.notification.event

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.out.QueryTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.SaveTopicSubscriptionPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateSubscribeTopicEvent(
    private val saveTopicSubscriptionPort: SaveTopicSubscriptionPort,
    private val queryTopicSubscriptionPort: QueryTopicSubscriptionPort
): CreateSubscribeTopicEventUseCase {

    @Transactional
    override fun execute(deviceToken: String) {
        val topicSubscription = queryTopicSubscriptionPort.queryAllNotificationByDeviceToken(deviceToken)
        if (topicSubscription == null) {
            val topics = listOf(
                Topic.NEW_NOTICE, Topic.APPLICATION,
                Topic.EARLY_RETURN, Topic.WEEKEND_MEAL
            )
            topics.forEach { it ->
                saveTopicSubscriptionPort.save(
                    TopicSubscription(
                        deviceToken = deviceToken,
                        isSubscribed = false,
                        topic = it
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
