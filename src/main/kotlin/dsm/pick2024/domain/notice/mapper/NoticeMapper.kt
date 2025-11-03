package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.admin.mapper.AdminMapper
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class NoticeMapper(private val adminMapper: AdminMapper) : GenericMapper<NoticeJpaEntity, Notice> {
    override fun toEntity(domain: Notice) = domain.run {
        NoticeJpaEntity(
            id = id,
            title = title,
            content = content,
            createAt = createAt,
            admin = adminMapper.toEntity(admin),
            teacherName = teacherName
        )
    }

    override fun toDomain(entity: NoticeJpaEntity) = entity.run {
        Notice(
            id = id!!,
            title = title,
            content = content,
            createAt = createAt,
            admin = adminMapper.toDomain(admin),
            teacherName = teacherName
        )
    }
}
