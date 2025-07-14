package dsm.pick2024.domain.attendance.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.entity.QAttendanceJpaEntity
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
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

    override fun save(attendance: Attendance) = attendanceJpaRepository.save(attendanceMapper.toEntity(attendance))
    override fun saveAll(attendance: List<Attendance>) {
        val entities = attendance.map { attendanceMapper.toEntity(it) }
        attendanceJpaRepository.saveAll(entities)
    }

    override fun findByUserId(userId: UUID) =
        attendanceJpaRepository.findByUserId(userId).let { attendanceMapper.toDomain(it) }

    override fun findAll() = attendanceJpaRepository.findAll().map { attendanceMapper.toDomain(it) }

    override fun findByFloor(floor: Int): List<Attendance>? =
        jpaQueryFactory
            .selectFrom(QAttendanceJpaEntity.attendanceJpaEntity)
            .where(
                QAttendanceJpaEntity.attendanceJpaEntity.floor.eq(floor),
                QAttendanceJpaEntity.attendanceJpaEntity.period6.ne(AttendanceStatus.DROPOUT)
            )
            .fetch()
            .map { attendanceMapper.toDomain(it) }

    override fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QAttendanceJpaEntity.attendanceJpaEntity)
        .where(
            QAttendanceJpaEntity.attendanceJpaEntity.grade.eq(grade),
            QAttendanceJpaEntity.attendanceJpaEntity.classNum.eq(classNum),
            QAttendanceJpaEntity.attendanceJpaEntity.period6.ne(AttendanceStatus.DROPOUT)
        )
        .fetch()
        .map { attendanceMapper.toDomain(it) }

    override fun findByClub(club: String) =
        jpaQueryFactory
            .selectFrom(QAttendanceJpaEntity.attendanceJpaEntity)
            .where(
                QAttendanceJpaEntity.attendanceJpaEntity.club.eq(club),
                QAttendanceJpaEntity.attendanceJpaEntity.period6.ne(AttendanceStatus.DROPOUT)
            )
            .fetch()
            .map { attendanceMapper.toDomain(it) }

    override fun findByStudentNum(grade: Int, classNum: Int, num: Int): Attendance? =
        attendanceJpaRepository.findByGradeAndClassNumAndNum(grade, classNum, num).let { attendanceMapper.toDomain(it) }
}
