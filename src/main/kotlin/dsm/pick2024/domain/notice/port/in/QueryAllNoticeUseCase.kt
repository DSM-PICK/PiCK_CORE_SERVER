package dsm.pick2024.domain.notice.port.`in`

import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleNoticeResponse
import java.util.UUID

interface QueryAllNoticeUseCase {

    fun queryAllSimpleNotice(): List<QuerySimpleNoticeResponse>

    fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse

    fun queryTodayNotice(): List<QuerySimpleNoticeResponse>
}
