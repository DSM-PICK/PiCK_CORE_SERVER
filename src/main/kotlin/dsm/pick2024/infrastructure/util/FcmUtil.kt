package dsm.pick2024.infrastructure.util

import com.google.firebase.messaging.*
import dsm.pick2024.domain.event.Topic
import dsm.pick2024.domain.notification.domain.TopicSubscription
import dsm.pick2024.domain.notification.port.out.CommendTopicSubscriptionPort
import org.springframework.stereotype.Component

@Component
class FcmUtil : CommendTopicSubscriptionPort {
    private val firebaseInstance: FirebaseMessaging
        get() = FirebaseMessaging.getInstance()

    override fun sendMessage(
        token: String,
        topicSubscription: TopicSubscription
    ) {
        val message = this.getMessageBuilderByTitleBody(topicSubscription.topic, "Title", "Body")
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
        topic: Topic,
        title: String,
        body: String
    ) {
        val message = this.getMessageBuilderByTitleBody(topic, title, body).build()
        firebaseInstance.sendAsync(message)
    }

    private fun getMessageBuilderByTitleBody(topic: Topic, title: String, body: String) =
        Message
            .builder()
            .setTopic(topic.toString())
            .setNotification(
                com.google.firebase.messaging.Notification
                    .builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .setApnsConfig(buildApnsConfig(topic, title, body))
            .setAndroidConfig(buildAndroidConfig(topic, title, body))

    private fun buildAndroidConfig(topic: Topic, title: String, body: String): AndroidConfig {
        return AndroidConfig.builder()
            .putData("topic", topic.name)
            .putData("title", title)
            .putData("body", body)
            .build()
    }

    private fun buildApnsConfig(topic: Topic, title: String, body: String): ApnsConfig {
        return ApnsConfig.builder()
            .setAps(
                Aps.builder()
                    .setSound("default")
                    .putCustomData("topic", topic.name)
                    .putCustomData("title", title)
                    .putCustomData("body", body)
                    .build()
            )
            .build()
    }
}
