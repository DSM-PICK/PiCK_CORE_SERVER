package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
abstract class NoticeMapper : GenericMapper<NoticeJpaEntity, Notice>{
    override fun toEntity(domain: Notice): NoticeJpaEntity =
        domain.run {
            NoticeJpaEntity(
                id = id,
                title = title,
                content = content,
                createAt = createAt,
                admin = admin
            )
        }

    override fun toDomain(entity: NoticeJpaEntity): Notice =
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
