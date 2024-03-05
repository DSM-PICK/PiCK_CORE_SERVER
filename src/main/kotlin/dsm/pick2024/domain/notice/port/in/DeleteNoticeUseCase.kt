package dsm.pick2024.domain.notice.port.`in`

import java.util.UUID

interface DeleteNoticeUseCase {
    fun deleteNotice(id: UUID)
}
