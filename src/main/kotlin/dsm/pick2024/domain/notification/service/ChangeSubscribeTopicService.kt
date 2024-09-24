package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.enums.Topic
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
        updateTopic(deviceToken, request.topic, request.isSubscribed)
        if (request.isSubscribed) {
            commendTopicSubscriptionPort.subscribeTopic(deviceToken, request.topic)
        } else {
            commendTopicSubscriptionPort.unsubscribeTopic(deviceToken, request.topic)
        }
        return request.isSubscribed
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
}
