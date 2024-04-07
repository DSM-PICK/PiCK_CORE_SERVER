package dsm.pick2024.domain.attendance.presentation

import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.QueryClassAttendanceUseCase
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
    private val queryClassAttendanceUseCase: QueryClassAttendanceUseCase
) {
    @Operation(summary = "자습 or 방과후 상태관리")
    @PatchMapping("/modify")
    fun changeAttendance(
        @RequestBody changeAttendanceRequest: List<ChangeAttendanceRequest>
    ) = changeAttendanceUseCase.changeAttendance(changeAttendanceRequest)

    @Operation(summary = "자습 교실 별 조회 api")
    @GetMapping("/grade")
    fun queryClassAttendance(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryClassAttendanceUseCase.queryClassAttendance(grade, classNum)
}
