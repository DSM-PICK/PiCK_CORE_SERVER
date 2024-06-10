package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.port.out.QueryNoticePort
import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QueryTodayNoticeResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryNoticeService(
    private val queryNoticePort: QueryNoticePort,
) : QueryAllNoticeUseCase {
    override fun queryAllSimpleNotice() =

        queryNoticePort.findAll()
            .map { it ->
                QuerySimpleAllNoticeResponse(
                    it.id!!,
                    it.title,
                    it.createAt,
                    it.teacherName,
                    it.grade.split(",").map { grade -> grade.toInt() }
                )
            }

    override fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse {
        val notice = queryNoticePort.findById(noticeId)
            ?: throw RuntimeException() //예외만들기

        return QueryAllNoticeResponse(
            title = notice.title,
            content = notice.content,
            createAt = notice.createAt,
            teacher = notice.teacherName,
            grade = notice.grade.split(",").map { grade -> grade.toInt() }
        )
    }

    override fun queryTodayNotice() =

        queryNoticePort.findByToday()
            .map { it ->
                QueryTodayNoticeResponse(
                    it.id!!,
                    it.title,
                    it.createAt,
                    it.teacherName
                )
            }
}
