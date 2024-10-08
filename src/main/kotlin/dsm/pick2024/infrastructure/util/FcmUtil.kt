package dsm.pick2024.infrastructure.util

import com.google.firebase.messaging.*
import dsm.pick2024.domain.event.enums.Topic
import dsm.pick2024.domain.notification.presentation.dto.request.Notification
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import dsm.pick2024.infrastructure.util.exception.FcmServerException
import org.springframework.stereotype.Component

@Component
class FcmUtil : CommendTopicSubscriptionPort {
    private val firebaseInstance: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()

    override fun sendMessage(
        token: List<String>,
        notification: Notification
    ) {
        val message = this.sendMessagesToDeviceToken(token, notification)
        try {
            firebaseInstance.sendMulticastAsync(message)
        } catch (e: FirebaseMessagingException) {
            throw FcmServerException
        }
    }

    override fun subscribeTopic(token: String, topic: Topic) {
        try {
            firebaseInstance.subscribeToTopic(listOf(token), topic.name)
        } catch (e: FirebaseMessagingException) {
            throw FcmServerException
        }
    }

    override fun unsubscribeTopic(token: String, topic: Topic) {
        try {
            firebaseInstance.unsubscribeFromTopicAsync(listOf(token), topic.name)
        } catch (e: FirebaseMessagingException) {
            throw FcmServerException
        }
    }

    override fun sendByTopic(
        notification: Notification
    ) {
        try {
            val message = this.sendMessageToTopic(notification).build()
            firebaseInstance.sendAsync(message)
        } catch (e: FirebaseMessagingException) {
            throw FcmServerException
        }
    }

    private fun buildNotification(notification: Notification): com.google.firebase.messaging.Notification.Builder {
        return com.google.firebase.messaging.Notification.builder()
            .setTitle(notification.title)
            .setBody(notification.body)
    }

    private fun sendMessagesToDeviceToken(token: List<String>, notification: Notification) =
        MulticastMessage.builder()
            .addAllTokens(token)
            .setNotification(buildNotification(notification).build())
            .setAndroidConfig(buildAndroidConfig(notification))
            .setApnsConfig(buildApnsConfig(notification))
            .build()

    private fun sendMessageToTopic(notification: Notification) =
        Message
            .builder()
            .setTopic(notification.topic.name)
            .setNotification(
                com.google.firebase.messaging.Notification
                    .builder()
                    .setTitle(notification.title)
                    .setBody(notification.body)
                    .build()
            )
            .setApnsConfig(buildApnsConfig(notification))
            .setAndroidConfig(buildAndroidConfig(notification))

    private fun buildAndroidConfig(notification: Notification): AndroidConfig {
        return AndroidConfig.builder()
            .putData("topic", notification.topic.name)
            .putData("title", notification.title)
            .putData("body", notification.body)
            .build()
    }

    private fun buildApnsConfig(notification: Notification): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(
                Aps.builder()
                    .setSound("default")
                    .putCustomData("topic", notification.topic.name)
                    .putCustomData("title", notification.title)
                    .putCustomData("body", notification.body)
                    .build()
            )
            .build()
    }
}
