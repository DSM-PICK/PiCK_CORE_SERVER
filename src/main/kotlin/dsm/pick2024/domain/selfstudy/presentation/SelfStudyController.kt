package dsm.pick2024.domain.selfstudy.presentation

import dsm.pick2024.domain.selfstudy.port.`in`.QueryDateSelfStudyUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.QueryMonthSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.QueryMySelfStudyUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.QueryTodaySelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.time.Month
import java.time.Year

@Tag(name = "selfStudy API")
@RestController
@RequestMapping("/self-study")
class SelfStudyController(
    private val selfStudyTeacherUseCase: SelfStudyTeacherUseCase,
    private val queryTodaySelfStudyTeacherUseCase: QueryTodaySelfStudyTeacherUseCase,
    private val querySelfStudyTeacherUseCase: QueryMonthSelfStudyTeacherUseCase,
    private val queryDateSelfStudyUseCase: QueryDateSelfStudyUseCase,
    private val queryMySelfStudyUseCase: QueryMySelfStudyUseCase
) {
    @Operation(summary = "자습감독 선생님 등록 API")
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/register")
    fun selfStudyTeacherRegister(
        @RequestBody registrationSelfStudyTeacherRequest: RegistrationSelfStudyTeacherRequest
    ) = selfStudyTeacherUseCase.registrationSelfStudyTeacher(registrationSelfStudyTeacherRequest)

    @Operation(summary = "자습감독 선생님 변경 API")
    @PatchMapping("/modify")
    fun modifySelfStudy(
        @RequestBody registrationSelfStudyTeacherRequest: RegistrationSelfStudyTeacherRequest
    ) = selfStudyTeacherUseCase.modifySelfStudyTeacher(registrationSelfStudyTeacherRequest)

    @Operation(summary = "당일 자습감독 선생님 조회")
    @GetMapping("/today")
    fun queryTodaySelfStudyTeacher(
        @RequestParam(name = "date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ) = queryTodaySelfStudyTeacherUseCase.queryTodaySelfStudyTeacher(date)

    @Operation(summary = "한달 자습감독 선생님 조회")
    @GetMapping("/month")
    fun queryMonthSelfStudyTeacher(
        @RequestParam(name = "year")
        year: Year,
        @RequestParam(name = "month")
        month: Month
    ) = querySelfStudyTeacherUseCase.queryMonthSelfStudyTeacher(year, month)

    @Operation(summary = "특정 날짜 자습감독 선생님 조회")
    @GetMapping("/date")
    fun queryDateSelfStudyTeacher(
        @RequestParam(name = "date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ) = queryDateSelfStudyUseCase.queryDateSelfStudy(date)

    @Operation(summary = "어드민 자습감독 확인 API")
    @GetMapping("/admin")
    fun queryMySelfStudy() = queryMySelfStudyUseCase.queryMySelfStudy()
}
