package dsm.pick2024.domain.notice.port.`in`

import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleAllNoticeResponse
import java.util.UUID

interface QueryAllNoticeUseCase {

    fun queryAllSimpleNotice(): List<QuerySimpleAllNoticeResponse>

    fun queryAllNotice(noticeId: UUID): QueryAllNoticeResponse

    fun queryTodayNotice(): List<QueryTodayNoticeResponse>
}
