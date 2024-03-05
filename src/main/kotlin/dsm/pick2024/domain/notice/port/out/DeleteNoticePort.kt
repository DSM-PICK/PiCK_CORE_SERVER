package dsm.pick2024.domain.notice.port.out

import java.util.UUID

interface DeleteNoticePort {
    fun deleteById(id: UUID)
}
