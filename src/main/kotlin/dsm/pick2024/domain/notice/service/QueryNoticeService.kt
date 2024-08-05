package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.exception.NoticeNotFoundException
import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.port.out.QueryNoticePort
import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleNoticeResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryNoticeService(
    private val queryNoticePort: QueryNoticePort
) : QueryAllNoticeUseCase {
    override fun queryAllSimpleNotice() =

        queryNoticePort.findAll()
            .map { it ->
                QuerySimpleNoticeResponse(
                    it.id!!,
                    it.title,
                    format(it.createAt),
                    it.teacherName
                )
            }

    override fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse {
        val notice = queryNoticePort.findById(noticeId)
            ?: throw NoticeNotFoundException

        return QueryAllNoticeResponse(
            title = notice.title,
            content = notice.content,
            createAt = format(notice.createAt),
            teacher = notice.teacherName
        )
    }

    override fun queryTodayNotice() =

        queryNoticePort.findByToday()
            .map { it ->
                QuerySimpleNoticeResponse(
                    it.id!!,
                    it.title,
                    format(it.createAt),
                    it.teacherName
                )
            }

    private fun format(date: LocalDateTime): String {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }
}
