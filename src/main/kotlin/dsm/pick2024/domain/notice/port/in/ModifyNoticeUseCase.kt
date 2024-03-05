package dsm.pick2024.domain.notice.port.`in`

import dsm.pick2024.domain.notice.presentation.dto.request.ModifyNoticeRequest

interface ModifyNoticeUseCase {
    fun modifyNotice(request: ModifyNoticeRequest)
}
