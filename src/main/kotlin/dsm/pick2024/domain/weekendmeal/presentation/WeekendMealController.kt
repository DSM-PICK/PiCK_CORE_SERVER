package dsm.pick2024.domain.weekendmeal.presentation

import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.port.`in`.ChangeWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.CreateWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryMyWeekendMealStatusUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealClassUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.SaveAllWeekendMealUserUseCase
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryStatusResponse
import dsm.pick2024.domain.weekendmeal.service.QueryAllWeekendMealStatus
import dsm.pick2024.domain.weekendmeal.service.WeekendMealExcelService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.servlet.http.HttpServletResponse

@Tag(name = "weekendMeal API")
@RestController
@RequestMapping("/weekend-meal")
class WeekendMealController(
    private val weekendMealUseCase: CreateWeekendMealUseCase,
    private val queryWeekendMealClassUseCase: QueryWeekendMealClassUseCase,
    private val queryMyWeekendMealStatusUseCase: QueryMyWeekendMealStatusUseCase,
    private val weekendMealExcelService: WeekendMealExcelService,
    private val changeWeekendMealStatusUseCase: ChangeWeekendMealStatusUseCase,
    private val saveAllWeekendMealUserUseCase: SaveAllWeekendMealUserUseCase,
    private val queryAllWeekendMealStatus: QueryAllWeekendMealStatus
) {
    @Operation(summary = "주말급식 강제 상태변경")
    @PatchMapping("/status")
    fun changeWeekendMealStatus(
        @RequestParam(name = "userId")
        userId: UUID,
        @RequestParam(name = "status")
        status: Status
    ) = changeWeekendMealStatusUseCase.changeWeekendMealStatus(userId, status)

    @Operation(summary = "내 주말급식 상태변경 API")
    @PatchMapping("/my-status")
    fun changeStatus(
        @RequestParam(name = "status") status: Status
    ) = weekendMealUseCase.changeWeekendMeal(status)

    @Operation(summary = "주말급식 응답자 반별로 조회 API")
    @GetMapping("/all")
    fun queryGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryWeekendMealClassUseCase.queryWeekendMealClass(grade, classNum)

    @Operation(summary = "주말급식 미응답자 반별로 조회 API")
    @GetMapping("/quit")
    fun queryQuitGradeAndClassNum(
        @RequestParam(name = "grade") grade: Int,
        @RequestParam(name = "class_num") classNum: Int
    ) = queryWeekendMealClassUseCase.queryWeekendMealQuitClass(grade, classNum)

    @Operation(summary = "내 주말급식 신청상태 조회 API")
    @GetMapping("/my")
    fun queryMyWeekendMealStatus(): QueryStatusResponse = queryMyWeekendMealStatusUseCase.queryMyWeekendMealStatus()

    @Operation(summary = "주말급식 신청자 엑셀 파일 출력 API")
    @GetMapping("/excel")
    fun getExcel(httpServletResponse: HttpServletResponse) = weekendMealExcelService.execute(httpServletResponse)

    @Operation(summary = "주말급식 유저 정보 저장 API")
    @PostMapping("/saveAll")
    fun saveAll(
        @RequestParam key: String
    ) = saveAllWeekendMealUserUseCase.saveAll(key)

    @Operation(summary = "주말급식 유저 전체 조회 API")
    @GetMapping("/hey")
    fun hey() = queryAllWeekendMealStatus.findAll()
}
