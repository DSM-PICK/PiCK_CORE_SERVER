package dsm.pick2024.domain.attendance.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.attendance.domain.Attendance
import dsm.pick2024.domain.attendance.mapper.AttendanceMapper
import dsm.pick2024.domain.attendance.persistence.repository.AttendanceRepository
import dsm.pick2024.domain.attendance.port.out.AttendancePort
import org.springframework.stereotype.Component
import java.util.*

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
}
