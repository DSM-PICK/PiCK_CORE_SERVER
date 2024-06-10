package dsm.pick2024.domain.notice.port.out

import dsm.pick2024.domain.notice.domain.Notice
import java.util.*

interface QueryNoticePort {

    fun findById(noticeId: UUID): Notice?

    fun findByToday(): List<Notice>
    
    fun findAll(): List<Notice>
}
