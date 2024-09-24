package dsm.pick2024.domain.notification.presentation.dto.request

import dsm.pick2024.domain.event.enums.Topic

data class Notification(
    val title: String,
    val body: String,
    val topic: Topic
)
