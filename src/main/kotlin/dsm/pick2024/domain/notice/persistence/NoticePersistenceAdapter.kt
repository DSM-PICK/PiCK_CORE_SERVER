package dsm.pick2024.domain.notice.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.persistence.repository.AdminRepository
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.QNoticeJpaEntity
import dsm.pick2024.domain.notice.mapper.NoticeMapper
import dsm.pick2024.domain.notice.persistence.repository.NoticeRepository
import dsm.pick2024.domain.notice.port.out.NoticePort
import org.springframework.stereotype.Component
import java.util.*

@Component
class NoticePersistenceAdapter(
    private val noticeRepository: NoticeRepository,
    private val noticeMapper: NoticeMapper,
    private val jpaQueryFactory: JPAQueryFactory,
    private val adminRepository: AdminRepository
) : NoticePort {
    override fun save(notice: Notice) {
        val admin = adminRepository.findById(notice.adminId).orElseThrow { AdminNotFoundException }
        noticeRepository.save(noticeMapper.toEntity(notice, admin))
    }

    override fun findById(noticeId: UUID) = noticeRepository.findById(noticeId).let { noticeMapper.toDomain(it) }

    override fun deleteById(id: UUID) = noticeRepository.deleteById(id)

    override fun findByToday(): List<Notice> {
        return jpaQueryFactory
            .selectFrom(QNoticeJpaEntity.noticeJpaEntity)
            .orderBy(QNoticeJpaEntity.noticeJpaEntity.createAt.desc())
            .limit(5)
            .fetch()
            .map { noticeMapper.toDomain(it) }
    }

    override fun findAll() =
        jpaQueryFactory
            .selectFrom(QNoticeJpaEntity.noticeJpaEntity)
            .orderBy(QNoticeJpaEntity.noticeJpaEntity.createAt.desc())
            .fetch()
            .map { noticeMapper.toDomain(it) }
}
