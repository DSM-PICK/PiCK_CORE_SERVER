package dsm.pick2024.domain.notification.service

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.Notification
import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.out.CommendNotificationPort
import dsm.pick2024.domain.notification.port.out.QueryNotificationPort
import dsm.pick2024.domain.notification.port.out.SaveNotificationPort
import dsm.pick2024.domain.user.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeSubscribeTopicService(
    private val queryNotificationPort: QueryNotificationPort,
    private val commendNotificationPort: CommendNotificationPort,
    private val saveNotificationPort: SaveNotificationPort,
    private val userFacade: UserFacade
) : ChangeTopicUseCase{

    @Transactional
    override fun changeSubscribeTopic(topic: Topic) {
        val user = userFacade.currentUser()
        val subscription = queryNotificationPort.queryNotificationByDeviceTokenAndTopic(user.deviceToken!!, topic)
    
        subscription?.let {
            commendNotificationPort.unsubscribeTopic(user.deviceToken, topic)

            val topicSubscription = Notification(
                deviceToken = user.deviceToken,
                topic = topic,
                isSubscribed = false
            )
            saveNotificationPort.save(topicSubscription)
        } ?: run {
            commendNotificationPort.subscribeTopic(user.deviceToken, topic)

            val topicSubscription = Notification(
                deviceToken = user.deviceToken,
                topic = topic,
                isSubscribed = true
            )
            saveNotificationPort.save(topicSubscription)
        }
    }
}
