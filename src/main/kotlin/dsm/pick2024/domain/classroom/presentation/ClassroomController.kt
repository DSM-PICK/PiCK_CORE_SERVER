package dsm.pick2024.domain.classroom.presentation

import dsm.pick2024.domain.classroom.port.`in`.QueryFloorClassroomUseCase
import dsm.pick2024.domain.classroom.port.`in`.QueryUserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.`in`.UserBackClassroomUseCase
import dsm.pick2024.domain.classroom.port.`in`.UserMoveClassroomUseCase
import dsm.pick2024.domain.classroom.port.out.QueryGradeClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.UserMoveClassroomRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Classroom API")
@RestController
@RequestMapping("/class-room")
class ClassroomController(
    private val userMoveClassroomUseCase: UserMoveClassroomUseCase,
    private val userBackClassroomUseCase: UserBackClassroomUseCase,
    private val queryUserMoveClassroomUseCase: QueryUserMoveClassroomUseCase,
    private val queryFloorClassroomUseCase: QueryFloorClassroomUseCase,
    private val queryGradeClassroomPort: QueryGradeClassroomPort
) {
    @Operation(summary = "교실이동 API")
    @PostMapping("/move")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun moveClassroom(@RequestBody userMoveClassroomRequest: UserMoveClassroomRequest) =
        userMoveClassroomUseCase.moveClassroom(userMoveClassroomRequest)

    @Operation(summary = "교실복귀 API")
    @DeleteMapping("/return")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun backClassroom() =
        userBackClassroomUseCase.backClassroom()

    @Operation(summary = "이동위치 조회 API")
    @GetMapping("/move")
    fun queryMoveClassroom() =
        queryUserMoveClassroomUseCase.queryUserMoveClassroom()

    @Operation(summary = "층별로 교실이동 조회 API")
    @GetMapping("/floor")
    fun queryFloorClassroom(@RequestParam floor: Int) =
        queryFloorClassroomUseCase.queryFloorClassroom(floor)

    @Operation(summary = "반별로 교실이동 조회 API")
    @GetMapping("/grade")
    fun queryGradeClassroom(
        @RequestParam grade: Int,
        @RequestParam classNum: Int
    ) = queryGradeClassroomPort.queryGradeClassroom(grade, classNum)
}
