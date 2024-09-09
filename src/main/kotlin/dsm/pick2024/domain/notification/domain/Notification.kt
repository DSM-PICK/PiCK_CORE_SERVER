package dsm.pick2024.domain.notification.domain

import dsm.pick2024.domain.event.Topic

data class Notification (
    val title: String,
    val body: String,
    val topic: Topic
)
