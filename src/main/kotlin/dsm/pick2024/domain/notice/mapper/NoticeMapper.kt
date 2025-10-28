package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class NoticeMapper : GenericMapper<NoticeJpaEntity, Notice> {
    override fun toEntity(domain: Notice): NoticeJpaEntity {
        TODO("Not yet implemented")
    }
    fun toEntity(domain: Notice, admin: AdminJpaEntity) =
        domain.run {
            NoticeJpaEntity(
                id = id,
                title = title,
                content = content,
                createAt = createAt,
                admin = admin,
                teacherName = teacherName
            )
        }

    override fun toDomain(entity: NoticeJpaEntity) = entity.run {
        Notice(
            id = id!!,
            title = title,
            content = content,
            createAt = createAt,
            adminId = admin.id!!,
            teacherName = teacherName
        )
    }
}
