package dsm.pick2024.domain.notification.port.`in`

import dsm.pick2024.domain.notification.presentation.dto.response.QueryMySubscribeTopicResponse

interface QueryMySubscribeTopicUseCase {
    fun execute(): QueryMySubscribeTopicResponse
}
