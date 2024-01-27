package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.admin.domain.Admin
import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import org.springframework.stereotype.Component

@Component
class NoticeMapper {
    fun toEntity(domain: Notice): NoticeJpaEntity =
        domain.run {
            NoticeJpaEntity(
                id = id,
                title = title,
                content = content,
                createAt = createAt,
                admin = admin,
            )
        }

    fun toDomain(entity: NoticeJpaEntity): Notice =
        entity.run {
            Notice(
                id = id!!,
                title = title,
                content = content,
                createAt = createAt,
                admin = admin
            )
        }
}
