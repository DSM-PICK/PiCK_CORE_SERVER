package dsm.pick2024.domain.notification.port.`in`

import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest

interface ChangeTopicUseCase {

    fun execute(request: ChangeSubscribeTopicRequest): Boolean
}
