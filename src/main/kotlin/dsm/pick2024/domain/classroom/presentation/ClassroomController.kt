package dsm.pick2024.domain.classroom.presentation

import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Classroom API")
@RestController
@RequestMapping("/class-room")
class ClassroomController(
    private val userMoveClassroomUseCase: UserMoveClassroomUseCase
) {
    @Operation(summary = "교실이동 API")
    @PostMapping("/move")
    @ResponseStatus(value = HttpStatus.OK)
    fun moveClassroom(@RequestBody userMoveClassroomRequest: UserMoveClassroomRequest) =
        userMoveClassroomUseCase.moveClassroom(userMoveClassroomRequest)
}
