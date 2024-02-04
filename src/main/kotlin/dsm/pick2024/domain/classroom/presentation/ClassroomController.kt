package dsm.pick2024.domain.classroom.presentation

import dsm.pick2024.domain.classroom.port.`in`.QueryUserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.`in`.UserBackClassroomUseCase
import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "Classroom API")
@RestController
@RequestMapping("/class-room")
class ClassroomController(
    private val userMoveClassroomUseCase: UserMoveClassroomUseCase,
    private val userBackClassroomUseCase: UserBackClassroomUseCase,
    private val queryUserMoveClassroomUseCase: QueryUserMoveClassroomUseCase
) {
    @Operation(summary = "교실이동 API")
    @PostMapping("/move")
    @ResponseStatus(value = HttpStatus.OK)
    fun moveClassroom(@RequestBody userMoveClassroomRequest: UserMoveClassroomRequest) =
        userMoveClassroomUseCase.moveClassroom(userMoveClassroomRequest)

    @Operation(summary = "교실복귀 API")
    @DeleteMapping("/back/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun backClassroom(@PathVariable id: UUID) =
        userBackClassroomUseCase.backClassroom(id)

    @Operation(summary = "이동위치 조회 API")
    @GetMapping("/move")
    fun queryMoveClassroom() =
        queryUserMoveClassroomUseCase.queryUserMoveClassroom()
}
