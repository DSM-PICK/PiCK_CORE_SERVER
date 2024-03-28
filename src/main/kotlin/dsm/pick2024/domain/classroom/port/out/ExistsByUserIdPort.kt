package dsm.pick2024.domain.classroom.port.out

import java.util.*

interface ExistsByUserIdPort {
    fun existsByUserId(userId: UUID): Boolean
}
