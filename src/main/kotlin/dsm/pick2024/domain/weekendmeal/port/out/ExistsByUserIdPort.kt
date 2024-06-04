package dsm.pick2024.domain.weekendmeal.port.out

import java.util.UUID

interface ExistsByUserIdPort {
    fun existsByUserId(id: UUID): Boolean
}
