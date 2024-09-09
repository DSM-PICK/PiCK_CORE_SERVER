package dsm.pick2024.domain.notification.presentation

import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notification")
class NotificationController(
    private val changeTopicUseCase: ChangeTopicUseCase
) {
    @PatchMapping("/change-subscribe")
    fun changeSubscribe(changeSubscribeTopicRequest: ChangeSubscribeTopicRequest) =
        changeTopicUseCase.execute(changeSubscribeTopicRequest)

}
