package dsm.pick2024.domain.notice.persistence.repository

import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NoticeRepository : JpaRepository<NoticeJpaEntity, UUID> {
}
