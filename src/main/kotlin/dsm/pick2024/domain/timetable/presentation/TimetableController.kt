package dsm.pick2024.domain.timetable.presentation

import dsm.pick2024.domain.timetable.service.QueryTimetableService
import dsm.pick2024.domain.timetable.service.SaveTimetableService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.time.DayOfWeek
import java.time.LocalDate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "시간표 api")
@RestController
@RequestMapping("/timetable")
class TimetableController(
    private val saveTimetableService: SaveTimetableService,
    private val queryTimetableService: QueryTimetableService
) {
    @Operation(summary = "시간표 저장 API")
    @PostMapping
    fun saveTimetableInfo() {
        saveTimetableService.saveTimetable()
    }

    @Operation(summary = "당일 시갖표 조회 API")
    @GetMapping("/today")
    fun getTodayTimetable() = queryTimetableService.queryDayTimetable()
}
