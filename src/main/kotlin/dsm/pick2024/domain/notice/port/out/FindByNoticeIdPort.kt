package dsm.pick2024.domain.notice.port.out

import dsm.pick2024.domain.notice.domain.Notice
import java.util.UUID

interface FindByNoticeIdPort {
    fun findById(noticeId: UUID): Notice?
}
