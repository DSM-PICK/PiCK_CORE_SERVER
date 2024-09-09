package dsm.pick2024.domain.notification.domain

data class Notification (
    val title: String,
    val body: String,
    val topic: dsm.pick2024.domain.event.Topic
)
