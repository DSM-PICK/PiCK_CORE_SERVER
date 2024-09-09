package dsm.pick2024.domain.notification.presentation.dto.response

import dsm.pick2024.domain.event.Topic

data class QueryMySubscribeTopicResponse (
    val subscribeTopicResponse: List<QuerySubscribeTopicResponse>
)

data class QuerySubscribeTopicResponse(
        val topic: Topic,
        val isSubscribed: Boolean
)

