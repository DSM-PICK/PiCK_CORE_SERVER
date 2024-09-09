package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.TopicSubscriptionPort
import dsm.pick2024.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ChangeSubscribeTopicService(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort,
    private val topicSubscriptionPort: TopicSubscriptionPort,
    private val userFacade: UserFacade
): ChangeTopicUseCase {

    override fun subscribeTopic(topic: Topic) {
        val user = userFacade.currentUser()
        updateTopic(user.deviceToken!!, topic, true)
        commendTopicSubscriptionPort.subscribeTopic(user.deviceToken, topic)
    }

    override fun unSubscribeTopic(topic: Topic) {
        val user = userFacade.currentUser()
        updateTopic(user.deviceToken!!, topic, false)
        commendTopicSubscriptionPort.unsubscribeTopic(user.deviceToken, topic)
    }

    private fun updateTopic(deviceToken: String, topic: Topic, isSubscription: Boolean) {
        val topicSubscription = topicSubscriptionPort.queryNotificationByDeviceTokenAndTopic(
            deviceToken, topic
        )
        topicSubscriptionPort.save(
            topicSubscription!!.copy(
                isSubscribed = isSubscription
            )
        )
    }
}
