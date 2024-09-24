package dsm.pick2024.domain.weekendmeal.presentation

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.*
import dsm.pick2024.domain.weekendmeal.presentation.dto.request.SettingWeekendMealPeriodRequest
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryStatusResponse
import dsm.pick2024.domain.weekendmeal.service.QueryAllWeekendMealStatus
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@Tag(name = "weekendMeal API")
@RestController
@RequestMapping("/weekend-meal")
class WeekendMealController(
    private val weekendMealUseCase: CreateWeekendMealUseCase,
    private val queryWeekendMealClassUseCase: QueryWeekendMealClassUseCase,
    private val queryMyWeekendMealStatusUseCase: QueryMyWeekendMealStatusUseCase,
    private val printExcelWeekendMealUseCase: PrintExcelWeekendMealUseCase,
    private val changeWeekendMealStatusUseCase: ChangeWeekendMealStatusUseCase,
    private val saveAllWeekendMealUserUseCase: SaveAllWeekendMealUserUseCase,
    private val queryAllWeekendMealStatus: QueryAllWeekendMealStatus,
    private val printExcelClassWeekendMealUseCase: PrintExcelClassWeekendMealUseCase,
    private val settingWeekendMealPeriodUseCase: SettingWeekendMealPeriodUseCase,
    private val queryIsWeekendMealPeriodUseCase: QueryIsWeekendMealPeriodUseCase,
    private val queryWeekendMealApplicationUseCase: QueryWeekendMealApplicationUseCase
) {

    @Operation(summary = "주말급식 강제 상태변경")
    @PatchMapping("/status")
    fun changeWeekendMealStatus(
        @RequestParam(name = "id")
        id: UUID,
        @RequestParam(name = "status")
        status: Status
    ) = changeWeekendMealStatusUseCase.changeWeekendMealStatus(id, status)

//    @Operation(summary = "내 주말급식 상태변경 API")
//    @PatchMapping("/my-status")
//    fun changeStatus(
//        @RequestParam(name = "status") status: Status
//    ) = weekendMealUseCase.changeWeekendMeal(status)

    @Operation(summary = "주말급식 응답자 반별로 조회 API")
    @GetMapping("/all")
    fun queryGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryWeekendMealClassUseCase.queryWeekendMealClass(grade, classNum)

    @Operation(summary = "내 주말급식 신청상태 조회 API")
    @GetMapping("/my")
    fun queryMyWeekendMealStatus(): QueryStatusResponse = queryMyWeekendMealStatusUseCase.queryMyWeekendMealStatus()

    @Operation(summary = "주말급식 신청자 엑셀 파일 출력 API")
    @GetMapping("/excel")
    fun getExcel(httpServletResponse: HttpServletResponse) = printExcelWeekendMealUseCase.execute(httpServletResponse)

    @Operation(summary = "반별 주말급식 신청자 엑셀 파일 출력 API")
    @GetMapping("/excel/grade")
    fun getClassExcel(
        httpServletResponse: HttpServletResponse,
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(
            name = "class_num"
        )classNum: Int
    ) =
        printExcelClassWeekendMealUseCase.execute(httpServletResponse, grade, classNum)

    @Operation(summary = "주말급식 유저 정보 저장 API")
    @PostMapping("/saveAll")
    fun saveAll(
        @RequestParam key: String
    ) = saveAllWeekendMealUserUseCase.saveAll(key)

    @Operation(summary = "주말급식 유저 전체 조회 API")
    @GetMapping("/hey")
    fun hey() = queryAllWeekendMealStatus.findAll()

    @Operation(summary = "주말급식 신청기간 변경 API")
    @PatchMapping("/period")
    fun settingWeekendMealPeriod(
        @Valid @RequestBody
        settingWeekendMealPeriodRequest: SettingWeekendMealPeriodRequest
    ) {
        settingWeekendMealPeriodUseCase.settingWeekendMealPeriod(settingWeekendMealPeriodRequest)
    }

    @Operation(summary = "메인 주말급식 신청기간 여부 조회 API")
    @GetMapping("/period")
    fun isWeekendMealPeriod() = queryIsWeekendMealPeriodUseCase.isWeekendMealPeriod()

    @Operation(summary = "주말급식 신청기간 조회 API")
    @GetMapping("/application")
    fun application() = queryWeekendMealApplicationUseCase.queryWeekendMealApplication()
}
