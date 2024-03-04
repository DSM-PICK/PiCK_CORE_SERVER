package dsm.pick2024.domain.notice.port.out

import dsm.pick2024.domain.notice.domain.Notice

interface NoticeSavePort {
    fun save(notice: Notice)
}
