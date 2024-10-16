package dsm.pick2024.domain.timetable.presentation

import dsm.pick2024.domain.timetable.port.`in`.ModifyTimetableUseCase
import dsm.pick2024.domain.timetable.port.`in`.QueryDayTimetableUseCase
import dsm.pick2024.domain.timetable.port.`in`.QueryTeacherTimetableUseCase
import dsm.pick2024.domain.timetable.port.`in`.QueryWeekTimetableUseCase
import dsm.pick2024.domain.timetable.presentation.dto.request.ChangeTimetableRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "시간표 api")
@RestController
@RequestMapping("/timetable")
class TimetableController(
    private val queryDayTimetableUseCase: QueryDayTimetableUseCase,
    private val queryWeekTimetableUseCase: QueryWeekTimetableUseCase,
    private val queryTeacherTimetableUseCase: QueryTeacherTimetableUseCase,
    private val modifyTimetableUseCase: ModifyTimetableUseCase
) {
    @Operation(summary = "당일 시간표 조회 API")
    @GetMapping("/today")
    fun getTodayTimetable() = queryDayTimetableUseCase.queryDayTimetable()

    @Operation(summary = "일주일 시간표 조회")
    @GetMapping("/week")
    fun getWeekTimetable() = queryWeekTimetableUseCase.queryWeekTimetable()

    @Operation(summary = "어드민 일주일 시간표 조회")
    @GetMapping("/all")
    fun getTeacherTimetable(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) =
        queryTeacherTimetableUseCase.queryTeacherTimetable(grade, classNum)
//
//    @Operation(summary = "과목 전체 조회")
//    @GetMapping("/subjects")
//    fun getSubjectNames() = querySubjectNameUseCase.getAllSubjectNames()

    @Operation(summary = "시간표 수정")
    @PatchMapping("/change")
    fun changeTimetable(@RequestBody request: ChangeTimetableRequest) =
        modifyTimetableUseCase.modifyTimetable(request)
}
