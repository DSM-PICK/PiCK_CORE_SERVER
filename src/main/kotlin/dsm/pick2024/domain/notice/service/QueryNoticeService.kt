package dsm.pick2024.domain.notice.service

import dsm.pick2024.domain.notice.port.`in`.QueryAllNoticeUseCase
import dsm.pick2024.domain.notice.port.out.FindByNoticeIdPort
import dsm.pick2024.domain.notice.port.out.QueryNoticeAllPort
import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleAllNoticeResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional(readOnly = true)
class QueryNoticeService(
    private val queryNoticeAllPort: QueryNoticeAllPort,
    private val findByNoticeIdPort: FindByNoticeIdPort
) : QueryAllNoticeUseCase {

    override fun queryAllSimpleNotice() =
        queryNoticeAllPort.findAll()
            .map { it ->
                QuerySimpleAllNoticeResponse(
                    it.id!!,
                    it.title,
                    it.createAt
                )
            }

    override fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse {
        val notice = findByNoticeIdPort.findById(noticeId) ?: throw RuntimeException()
        return QueryAllNoticeResponse(
            notice.title,
            notice.content,
            notice.createAt,
            notice.teacher
        )
    }
}
