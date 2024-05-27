package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.port.out.FindByNoticeIdPort
import dsm.pick2024.domain.notice.port.out.FindByTodayPort
import dsm.pick2024.domain.notice.port.out.QueryNoticeAllPort
import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QueryTodayNoticeResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryNoticeService(
    private val queryNoticeAllPort: QueryNoticeAllPort,
    private val findByNoticeIdPort: FindByNoticeIdPort,
    private val findByTodayPort: FindByTodayPort
) : QueryAllNoticeUseCase {
    override fun queryAllSimpleNotice() =

        queryNoticeAllPort.findAll()
            .map { it ->
                QuerySimpleAllNoticeResponse(
                    it.id!!,
                    it.title,
                    it.createAt,
                    it.teacher,
                    it.grade.split(",").map { grade -> grade.toInt() }
                )
            }

    override fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse {
        val notice = findByNoticeIdPort.findById(noticeId)
            ?: throw RuntimeException() //예외만들기

        return QueryAllNoticeResponse(
            title = notice.title,
            content = notice.content,
            createAt = notice.createAt,
            teacher = notice.teacher,
            grade = notice.grade.split(",").map { grade -> grade.toInt() }
        )
    }

    override fun queryTodayNotice() =

        findByTodayPort.findByToday()
            .map { it ->
                QueryTodayNoticeResponse(
                    it.id!!,
                    it.title,
                    it.createAt,
                    it.teacher
                )
            }
}
