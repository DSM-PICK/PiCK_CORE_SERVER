package dsm.pick2024.domain.notification.presentation

import dsm.pick2024.domain.notification.port.`in`.ChangeTopicUseCase
import dsm.pick2024.domain.notification.port.`in`.QueryMySubscribeTopicUseCase
import dsm.pick2024.domain.notification.presentation.dto.request.ChangeSubscribeTopicRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "알림 api")
@RestController
@RequestMapping("/notification")
class NotificationController(
    private val changeTopicUseCase: ChangeTopicUseCase,
    private val queryMySubscribeTopicUseCase: QueryMySubscribeTopicUseCase
) {
    @Operation(summary = "topic 구독상태 변경")
    @PatchMapping("/update-subscribe")
    fun changeSubscribe(@RequestBody changeSubscribeTopicRequest: ChangeSubscribeTopicRequest) =
        changeTopicUseCase.execute(changeSubscribeTopicRequest)

    @Operation(summary = "내 구독여부 조회")
    @GetMapping("/my-subscribe")
    fun queryMySubscribe() =
        queryMySubscribeTopicUseCase.execute()
}
