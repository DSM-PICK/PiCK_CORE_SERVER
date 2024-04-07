package dsm.pick2024.domain.attendance.presentation

import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.port.`in`.SaveAllAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "attendance API")
@RestController
@RequestMapping("/attendance")
class AttendanceController(
    private val saveAllAttendanceUseCase: SaveAllAttendanceUseCase,
    private val changeAttendanceUseCase: ChangeAttendanceUseCase
) {

    @Operation(summary = "데이터 저장 api")
    @PostMapping("/all")
    fun all(@RequestParam(name = "key") key: String) =
        saveAllAttendanceUseCase.saveAll(key)

    @Operation(summary = "자습 or 방과후 상태관리")
    @PatchMapping("/modify")
    fun changeAttendance(changeAttendanceRequest: List<ChangeAttendanceRequest>) =
        changeAttendanceUseCase.changeAttendance(changeAttendanceRequest)
}
