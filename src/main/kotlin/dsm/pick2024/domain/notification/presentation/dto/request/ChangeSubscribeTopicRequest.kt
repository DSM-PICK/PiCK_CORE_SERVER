package dsm.pick2024.domain.notification.presentation.dto.request

import dsm.pick2024.domain.event.Topic

data class ChangeSubscribeTopicRequest(
    val isSubscribed: Boolean,
    val topic: Topic
)
