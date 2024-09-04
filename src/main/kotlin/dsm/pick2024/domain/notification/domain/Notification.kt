package dsm.pick2024.domain.notification.domain

import dsm.pick2024.domain.event.Topic
import dsm.pick2024.global.annotation.Aggregate
import java.time.LocalDateTime
import java.util.UUID

@Aggregate
data class Notification(
    val id: UUID? = null,
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false
)
