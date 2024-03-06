package dsm.pick2024.domain.notice.presentation

import dsm.pick2024.domain.notice.port.`in`.CreateNoticeUseCase
import dsm.pick2024.domain.notice.port.`in`.DeleteNoticeUseCase
import dsm.pick2024.domain.notice.port.`in`.ModifyNoticeUseCase
import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.presentation.dto.request.ModifyNoticeRequest
import dsm.pick2024.domain.notice.presentation.dto.request.NoticeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/notice")
class NoticeController(
    private val createNoticeUseCase: CreateNoticeUseCase,
    private val modifyNoticeUseCase: ModifyNoticeUseCase,
    private val deleteNoticeUseCase: DeleteNoticeUseCase,
    private val queryAllNoticeUseCase: QueryAllNoticeUseCase
) {

    @Operation(summary = "공지 생성 API")
    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createNotice(noticeRequest: NoticeRequest) =
        createNoticeUseCase.createNotice(noticeRequest)

    @Operation(summary = "공지 수정 API")
    @PatchMapping("/modify")
    fun modifyNotice(@RequestBody modifyNoticeRequest: ModifyNoticeRequest) =
        modifyNoticeUseCase.modifyNotice(modifyNoticeRequest)

    @Operation(summary = "공지 삭제 API")
    @DeleteMapping("/delete/{noticeId}")
    fun deleteNotice(@PathVariable(name = "noticeId") id: UUID) =
        deleteNoticeUseCase.deleteNotice(id)

    @Operation(summary = "공지 간편 조회 API")
    @GetMapping("/simple")
    fun queryTitleNotice() =
        queryAllNoticeUseCase.queryAllSimpleNotice()

    @Operation(summary = "공지 상세 조회 API")
    @GetMapping("/{noticeId}")
    fun queryDetailNotice(@PathVariable noticeId: UUID) =
        queryAllNoticeUseCase.queryAllNotice(noticeId)

    @Operation(summary = "오늘 공지 조회 API")
    @GetMapping("/today")
    fun queryTodayNotice() =
        queryAllNoticeUseCase.queryTodayNotice()
}
