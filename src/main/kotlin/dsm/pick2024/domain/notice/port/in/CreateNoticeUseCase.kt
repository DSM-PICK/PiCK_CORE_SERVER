package dsm.pick2024.domain.notice.port.`in`

import dsm.pick2024.domain.notice.presentation.dto.request.NoticeRequest

interface CreateNoticeUseCase {
    fun createNotice(request: NoticeRequest)
}
