package dsm.pick2024.domain.event.sub

import dsm.pick2024.domain.application.port.`in`.UpdateReturnTeacherUseCase
import dsm.pick2024.domain.event.dto.ChangeStatusRequest
import dsm.pick2024.domain.event.dto.UserInfoRequest
import dsm.pick2024.domain.event.enums.EventTopic
import dsm.pick2024.domain.main.port.`in`.MainUseCase
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class EventService(
    private val mainUseCase: MainUseCase,
    private val updateReturnTeacherUseCase: UpdateReturnTeacherUseCase
) {

    @EventListener(ChangeStatusRequest::class)
    fun onChangeApplicationStatusEvent(event: ChangeStatusRequest) {
        event.userIdList.forEach {
            mainUseCase.onHandleEvent(it)
        }
    }

    @EventListener(UserInfoRequest::class)
    fun onCreateApplicationEvent(event: UserInfoRequest) {
        when (event.source) {
            EventTopic.UPDATE_RETURN_TEACHER ->
                updateReturnTeacherUseCase.updateReturnTeacher(event.userId)
            EventTopic.HANDLE_EVENT ->
                mainUseCase.onHandleEvent(event.userId)
        }
    }
}
