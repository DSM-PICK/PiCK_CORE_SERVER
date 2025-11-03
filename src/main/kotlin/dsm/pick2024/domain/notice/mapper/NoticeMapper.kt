package dsm.pick2024.domain.notice.mapper

import dsm.pick2024.domain.admin.entity.AdminJpaEntity
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.NoticeJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class NoticeMapper : GenericMapper<NoticeJpaEntity, Notice> {

    fun toEntity(domain: Notice, admin: AdminJpaEntity): NoticeJpaEntity =
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

    override fun toDomain(entity: NoticeJpaEntity): Notice =
        entity.run {
            Notice(
                id = id!!,
                title = title,
                content = content,
                createAt = createAt,
                adminId = admin.id!!,
                teacherName = teacherName
            )
        }

    override fun toEntity(domain: Notice): NoticeJpaEntity {
        throw UnsupportedOperationException("AdminJpaEntity는 반드시 명시적으로 전달되어야 합니다.")
    }
}
