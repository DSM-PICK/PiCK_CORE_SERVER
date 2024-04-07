package dsm.pick2024.domain.attendance.persistence

import dsm.pick2024.domain.attendance.mapper.AttendanceMapper
import dsm.pick2024.domain.attendance.persistence.repository.AttendanceJpaRepository
import org.springframework.stereotype.Component

@Component
class AttendancePersistenceAdapter(
    private val attendanceJpaRepository: AttendanceJpaRepository,
    private val attendanceMapper: AttendanceMapper
) {
}
