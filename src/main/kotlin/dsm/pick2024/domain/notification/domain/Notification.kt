package dsm.pick2024.domain.notification.domain

import dsm.pick2024.domain.event.Topic
import java.time.LocalDateTime

data class Notification(
    val id: Long,
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false
)
