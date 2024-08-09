package dsm.pick2024.domain.notice.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.notice.domain.Notice
import dsm.pick2024.domain.notice.entity.QNoticeJpaEntity
import dsm.pick2024.domain.notice.mapper.NoticeMapper
import dsm.pick2024.domain.notice.persistence.repository.NoticeRepository
import dsm.pick2024.domain.notice.port.out.NoticePort
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class NoticePersistenceAdapter(
    private val noticeRepository: NoticeRepository,
    private val noticeMapper: NoticeMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : NoticePort {
    override fun save(notice: Notice) = noticeRepository.save(noticeMapper.toEntity(notice))

    override fun findById(noticeId: UUID) = noticeRepository.findById(noticeId).let { noticeMapper.toDomain(it) }

    override fun deleteById(id: UUID) = noticeRepository.deleteById(id)

    override fun findByToday(): List<Notice> {
        val today = LocalDateTime.now(ZoneId.of("Asia/Seoul"))

        return jpaQueryFactory
            .selectFrom(QNoticeJpaEntity.noticeJpaEntity)
            .where(
                QNoticeJpaEntity.noticeJpaEntity.createAt.year().eq(today.year)
                    .and(QNoticeJpaEntity.noticeJpaEntity.createAt.month().eq(today.monthValue))
                    .and(QNoticeJpaEntity.noticeJpaEntity.createAt.dayOfMonth().eq(today.dayOfMonth))
            )
            .fetch()
            .map { noticeMapper.toDomain(it) }
            .sortedByDescending { it.createAt }
    }


    override fun findAll() =
        jpaQueryFactory
            .selectFrom(QNoticeJpaEntity.noticeJpaEntity)
            .orderBy(QNoticeJpaEntity.noticeJpaEntity.createAt.desc())
            .fetch()
            .map { noticeMapper.toDomain(it) }
}
