package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.TopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest
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

    override fun execute(request: ChangeSubscribeTopicRequest): Boolean {
        val user = userFacade.currentUser()
        if (request.isSubscribed) {
            updateTopic(user.deviceToken!!, request.topic, true)
            commendTopicSubscriptionPort.subscribeTopic(user.deviceToken, request.topic)
            return true
        } else {
            updateTopic(user.deviceToken!!, request.topic, false)
            commendTopicSubscriptionPort.unsubscribeTopic(user.deviceToken, request.topic)
            return false
        }
    }

    private fun updateTopic(deviceToken: String, topic: Topic, isSubscribed: Boolean) {
        val topicSubscription = topicSubscriptionPort.queryNotificationByDeviceTokenAndTopic(
            deviceToken, topic
        )
        topicSubscriptionPort.save(
            topicSubscription!!.copy(
                isSubscribed = isSubscribed
            )
        )
    }
}
