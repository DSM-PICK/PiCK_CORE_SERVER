package dsm.pick2024.domain.attendance.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.entity.QAttendanceJpaEntity
import dsm.pick2024.domain.attendance.mapper.AttendanceMapper
import dsm.pick2024.domain.attendance.persistence.repository.AttendanceRepository
import dsm.pick2024.domain.attendance.port.out.AttendancePort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AttendancePersistenceAdapter(
    private val attendanceJpaRepository: AttendanceRepository,
    private val attendanceMapper: AttendanceMapper,
    private val jpaQueryFactory: JPAQueryFactory
) : AttendancePort {
    override fun saveAll(attendance: MutableList<Attendance>) {
        val entities = attendance.map { attendanceMapper.toEntity(it) }
        attendanceJpaRepository.saveAll(entities)
    }

    override fun findByUserId(userId: UUID) =
        attendanceJpaRepository.findByUserId(userId).let { attendanceMapper.toDomain(it) }

    override fun findAll() = attendanceJpaRepository.findAll().map { attendanceMapper.toDomain(it) }

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QAttendanceJpaEntity.attendanceJpaEntity)
        .where(
            QAttendanceJpaEntity.attendanceJpaEntity.grade.eq(grade),
            QAttendanceJpaEntity.attendanceJpaEntity.classNum.eq(classNum)
        )
        .orderBy(
            QAttendanceJpaEntity.attendanceJpaEntity.num.asc()
        )
        .fetch()
        .map { attendanceMapper.toDomain(it) }

    override fun findByClub(club: String) =
        jpaQueryFactory
            .selectFrom(QAttendanceJpaEntity.attendanceJpaEntity)
            .where(
                QAttendanceJpaEntity.attendanceJpaEntity.club.eq(club)
            )
            .orderBy(
                QAttendanceJpaEntity.attendanceJpaEntity.grade.asc(),
                QAttendanceJpaEntity.attendanceJpaEntity.classNum.asc(),
                QAttendanceJpaEntity.attendanceJpaEntity.num.asc()
            )
            .fetch()
            .map { attendanceMapper.toDomain(it) }
}
