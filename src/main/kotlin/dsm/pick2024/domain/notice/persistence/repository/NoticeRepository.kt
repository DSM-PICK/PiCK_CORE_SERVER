package dsm.pick2024.domain.notice.persistence.repository

import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import org.springframework.data.repository.Repository
import java.util.UUID

interface NoticeRepository : Repository<NoticeJpaEntity, UUID> {
    fun save(entity: NoticeJpaEntity)

    fun findById(id: UUID): NoticeJpaEntity

    fun deleteById(id: UUID)
}
