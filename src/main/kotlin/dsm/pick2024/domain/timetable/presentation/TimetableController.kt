package dsm.pick2024.domain.timetable.presentation

import dsm.pick2024.domain.timetable.service.SaveTimetableService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "시간표 api")
@RestController
@RequestMapping("/timetable")
class TimetableController(
    private val saveTimetableService: SaveTimetableService
) {
    @Operation(summary = "시간표 저장 API")
    @PostMapping
    fun saveTimetableInfo() {
        saveTimetableService.saveTimetable()
    }
}
