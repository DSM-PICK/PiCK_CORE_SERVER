package dsm.pick2024.domain.attendance.presentation

import dsm.pick2024.domain.attendance.port.`in`.ChangeAttendanceUseCase
import dsm.pick2024.domain.attendance.presentation.dto.request.ChangeAttendanceRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PatchMapping
<<<<<<< HEAD
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
=======
>>>>>>> 111afd95027e312d8322cd36fb95eb72ece7c060
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "attendance API")
@RestController
@RequestMapping("/attendance")
class AttendanceController(
    private val changeAttendanceUseCase: ChangeAttendanceUseCase
) {
    @Operation(summary = "자습 or 방과후 상태관리")
    @PatchMapping("/modify")
    fun changeAttendance(
        @RequestBody changeAttendanceRequest: List<ChangeAttendanceRequest>) =
        changeAttendanceUseCase.changeAttendance(changeAttendanceRequest)
}
