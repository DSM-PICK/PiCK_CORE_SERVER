package dsm.pick2024.infrastructure.util

import com.google.firebase.messaging.*
import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.Notification
import dsm.pick2024.domain.notification.NotificationPort
import org.springframework.stereotype.Component

@Component
class FcmUtil : NotificationPort {
    private val firebaseInstance: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()

    override fun sendMessage(
        token: String,
        notification: Notification
    ) {
        val message = this.getMassageBuilderByNotification(notification)
            .setToken(token)
            .build()
        firebaseInstance.sendAsync(message)
    }

    override fun subscribeTopic(token: String, topic: Topic) {
        firebaseInstance.subscribeToTopicAsync(listOf(token), topic.toString())
    }

    override fun unsubscribeTopic(token: String, topic: Topic) {
        firebaseInstance.unsubscribeFromTopicAsync(listOf(token), topic.toString())
    }


    override fun sendByTopic(
        notification: Notification
    ) {
        val message = this.getMassageBuilderByNotification(notification).build()
        firebaseInstance.sendAsync(message)
    }

    private fun getMassageBuilderByNotification(notification: Notification) =
        with(notification) {
            Message
                .builder()
                .setTopic(topic.toString())
                .setNotification(
                    com.google.firebase.messaging.Notification
                        .builder()
                        .setTitle(title)
                        .setBody(content)
                        .build()
                )
                .setApnsConfig(buildApnsConfig(notification))
                .setAndroidConfig(buildAndroidConfig(notification))
        }

    private fun buildAndroidConfig(notification: Notification): AndroidConfig {
        return AndroidConfig.builder()
            .putData("topic", notification.topic.name)
            .build()
    }

    private fun buildApnsConfig(notification: Notification): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(
                Aps.builder()
                    .setSound("default")
                    .putCustomData("topic", notification.topic.name)
                    .build()
            )
            .build()
    }

}
