package dsm.pick2024.domain.notification.domain

import dsm.pick2024.domain.event.enums.Topic
import dsm.pick2024.global.annotation.Aggregate
import java.util.UUID

@Aggregate
data class TopicSubscription(
    val id: UUID = UUID(0,0),
    val topic: Topic,
    val deviceToken: String,
    val isSubscribed: Boolean = false,
    val userId: String
)
