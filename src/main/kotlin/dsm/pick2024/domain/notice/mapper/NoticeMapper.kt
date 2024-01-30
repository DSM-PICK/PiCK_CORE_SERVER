package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.apache.ibatis.annotations.Mapper

@Mapper
abstract class NoticeMapper : GenericMapper<NoticeJpaEntity, Notice> {
    abstract override fun toEntity(domain: Notice): NoticeJpaEntity

    abstract override fun toDomain(entity: NoticeJpaEntity?): Notice?
}
