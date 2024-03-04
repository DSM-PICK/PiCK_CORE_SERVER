package dsm.pick2024.domain.notice.presentation

import dsm.pick2024.domain.notice.port.`in`.CreateNoticeUseCase
import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.presentation.dto.request.CreateNoticeRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@Tag(name = "공지사항 API")
@RestController
@RequestMapping("/notice")
class NoticeController(
    private val createNoticeUseCase: CreateNoticeUseCase,
    private val queryAllNoticeUseCase: QueryAllNoticeUseCase
) {

    @Operation(summary = "공지 생성 API")
    @PostMapping("/create")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createNotice(createNoticeRequest: CreateNoticeRequest) =
        createNoticeUseCase.createNotice(createNoticeRequest)

    @Operation(summary = "공지 제목 조회 API")
    @GetMapping("/title/{noticeId}")
    fun queryTitleNotice(@PathVariable noticeId: UUID) =
        queryAllNoticeUseCase.queryAllSimpleNotice()
}
