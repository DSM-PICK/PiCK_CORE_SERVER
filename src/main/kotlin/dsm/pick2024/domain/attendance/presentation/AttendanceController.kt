package dsm.pick2024.domain.attendance.presentation

import dsm.pick2024.domain.attendance.port.`in`.ChangeAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAllAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClubAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAllAttendanceRequest
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
    private val queryClubAttendanceUseCase: QueryClubAttendanceUseCase,
    private val queryClassAllAttendanceUseCase: QueryClassAllAttendanceUseCase,
    private val queryClubAllAttendanceUseCase: QueryClubAllAttendanceUseCase,
    private val changeAllAttendanceUseCase: ChangeAllAttendanceUseCase
) {
    @Operation(summary = "자습 or 동아리 상태관리")
    @PatchMapping("/modify")
    fun changeAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestBody changeAttendanceRequest: List<ChangeAttendanceRequest>
    ) = changeAttendanceUseCase.changeAttendance(period, changeAttendanceRequest)

    @PatchMapping("/alltime/modify")
    fun changeAllAttendance(
        @RequestBody changeAllAttendanceRequest: List<ChangeAllAttendanceRequest>
    ) = changeAllAttendanceUseCase.changeAllAttendance(changeAllAttendanceRequest)

    @Operation(summary = "자습 교실 별 조회 api")
    @GetMapping("/grade")
    fun queryClassAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassAttendanceUseCase.queryClassAttendance(period, grade, classNum)

    @Operation(summary = "어드민 자습 교실 별 조회 api")
    @GetMapping("/alltime/grade")
    fun queryClassAllAttendance(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassAllAttendanceUseCase.queryClassAllAttendance(grade, classNum)

    @Operation(summary = "동아리실 별 조회 api")
    @GetMapping("/club")
    fun queryClubAttendance(
        @RequestParam(name = "period") period: Int,
        @RequestParam(name = "club") club: String
    ) = queryClubAttendanceUseCase.queryClubAttendance(period, club)

    @Operation(summary = "동아리실 별 조회 api")
    @GetMapping("/alltime/club")
    fun queryClubAttendance(
        @RequestParam(name = "club") club: String
    ) = queryClubAllAttendanceUseCase.queryClubAllAttendance(club)
}
