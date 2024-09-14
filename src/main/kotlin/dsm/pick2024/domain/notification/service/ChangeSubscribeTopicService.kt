package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.domain.notification.port.out.TopicSubscriptionPort
import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest
import dsm.pick2024.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeSubscribeTopicService(
    private val commendTopicSubscriptionPort: CommendTopicSubscriptionPort,
    private val topicSubscriptionPort: TopicSubscriptionPort,
    private val userFacade: UserFacade
) : ChangeTopicUseCase {

    @Transactional
    override fun execute(request: ChangeSubscribeTopicRequest): Boolean {
        val deviceToken = userFacade.currentUser().deviceToken!!

        if (request.topic == Topic.ALL) {
            updateAllTopics(deviceToken, request.isSubscribed)
        } else {
            handleSingleTopic(deviceToken, request.topic, request.isSubscribed)
        }
        return request.isSubscribed
    }

    private fun handleSingleTopic(deviceToken: String, topic: Topic, isSubscribed: Boolean) {
        updateTopic(deviceToken, topic, isSubscribed)
        if (isSubscribed) {
            commendTopicSubscriptionPort.subscribeTopic(deviceToken, topic)
        } else {
            commendTopicSubscriptionPort.unsubscribeTopic(deviceToken, topic)
        }
    }

    private fun updateTopic(deviceToken: String, topic: Topic, isSubscribed: Boolean) {
        val topicSubscription = topicSubscriptionPort.queryTopicSubscriptionByDeviceTokenAndTopic(
            deviceToken,
            topic
        )
        topicSubscriptionPort.save(
            topicSubscription!!.copy(
                isSubscribed = isSubscribed
            )
        )
    }

    private fun updateAllTopics(deviceToken: String, isSubscribed: Boolean) {
        Topic.values().filter { it != Topic.ALL }.forEach { topic ->
            updateTopic(deviceToken, topic, isSubscribed)
        }
    }
}
