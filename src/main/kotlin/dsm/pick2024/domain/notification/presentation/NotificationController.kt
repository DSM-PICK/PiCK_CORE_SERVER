package dsm.pick2024.domain.notification.presentation

import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "알림 api")
@RestController
@RequestMapping("/notification")
class NotificationController(
    private val changeTopicUseCase: ChangeTopicUseCase
) {
    @Operation(summary = "topic 구독상태 변경")
    @PatchMapping("/change-subscribe")
    fun changeSubscribe(changeSubscribeTopicRequest: ChangeSubscribeTopicRequest) =
        changeTopicUseCase.execute(changeSubscribeTopicRequest)

}
