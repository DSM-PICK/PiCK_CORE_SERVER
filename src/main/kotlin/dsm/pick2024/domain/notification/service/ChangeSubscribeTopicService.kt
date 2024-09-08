package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.QueryTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.SaveTopicSubscriptionPort
import dsm.pick2024.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeSubscribeTopicService(
    private val queryTopicSubscriptionPort: QueryTopicSubscriptionPort,
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort,
    private val saveTopicSubscriptionPort: SaveTopicSubscriptionPort,
    private val userFacade: UserFacade
) : ChangeTopicUseCase{

    @Transactional
    override fun changeSubscribeTopic(topic: Topic) {
        val user = userFacade.currentUser()
        val subscription = queryTopicSubscriptionPort.queryNotificationByDeviceTokenAndTopic(user.deviceToken!!, topic)
    
        subscription?.let {
            commendTopicSubscriptionPort.unsubscribeTopic(user.deviceToken, topic)

            val topicSubscription = TopicSubscription(
                deviceToken = user.deviceToken,
                topic = topic,
                isSubscribed = false
            )
            saveTopicSubscriptionPort.save(topicSubscription)
        } ?: run {
            commendTopicSubscriptionPort.subscribeTopic(user.deviceToken, topic)

            val topicSubscription = TopicSubscription(
                deviceToken = user.deviceToken,
                topic = topic,
                isSubscribed = true
            )
            saveTopicSubscriptionPort.save(topicSubscription)
        }
    }
}
