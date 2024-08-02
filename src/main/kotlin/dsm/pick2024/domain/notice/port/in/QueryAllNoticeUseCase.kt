package dsm.pick2024.domain.notice.port.`in`

import dsm.pick2024.domain.notice.presentation.dto.response.QueryAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QuerySimpleAllNoticeResponse
import dsm.pick2024.domain.notice.presentation.dto.response.QueryTodayNoticeResponse
import java.util.UUID

interface QueryAllNoticeUseCase {

    fun queryAllSimpleNotice(pageSize: Long, noticeId: UUID?): List<QuerySimpleAllNoticeResponse>

    fun queryDetailNotice(noticeId: UUID): QueryAllNoticeResponse

    fun queryTodayNotice(): List<QueryTodayNoticeResponse>
}
