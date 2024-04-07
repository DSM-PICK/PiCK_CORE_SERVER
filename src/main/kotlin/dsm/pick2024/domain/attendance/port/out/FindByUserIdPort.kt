package dsm.pick2024.domain.attendance.port.out

import dsm.pick2024.domain.attendance.domain.Attendance
import java.util.UUID

interface FindByUserIdPort {
    fun findByUserId(userId: UUID): Attendance?
}
