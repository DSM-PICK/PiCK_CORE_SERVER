package dsm.pick2024.domain.attendance.presentation

import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "attendance API")
@RestController
@RequestMapping("/attendance")
class AttendanceController(
    private val changeAttendanceUseCase: ChangeAttendanceUseCase,
    private val queryClassAttendanceUseCase: QueryClassAttendanceUseCase,
    private val queryClubAttendanceUseCase: QueryClubAttendanceUseCase
) {
    @Operation(summary = "자습 or 동아리 상태관리")
    @PatchMapping("/modify")
    fun changeAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestBody changeAttendanceRequest: List<ChangeAttendanceRequest>
    ) = changeAttendanceUseCase.changeAttendance(period, changeAttendanceRequest)

    @Operation(summary = "자습 교실 별 조회 api")
    @GetMapping("/grade")
    fun queryClassAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassAttendanceUseCase.queryClassAttendance(period, grade, classNum)

    @Operation(summary = "동아리실 별 조회 api")
    @GetMapping("/club")
    fun queryClubAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestParam(name = "club") club: String
    ) = queryClubAttendanceUseCase.queryClubAttendance(period, club)
}
