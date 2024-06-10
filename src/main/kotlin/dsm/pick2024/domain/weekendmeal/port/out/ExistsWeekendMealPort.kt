package dsm.pick2024.domain.weekendmeal.port.out

import java.util.UUID

interface ExistsWeekendMealPort {
    fun existsByUserId(id: UUID): Boolean
}
