package dsm.pick2024.domain.attendance.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.attendance.mapper.AttendanceMapper
import dsm.pick2024.domain.attendance.persistence.repository.AttendanceRepository
import org.springframework.stereotype.Component

@Component
class AttendancePersistenceAdapter(
    private val attendanceJpaRepository: AttendanceRepository,
    private val attendanceMapper: AttendanceMapper,
    private val jpaQueryFactory: JPAQueryFactory
) {
}
