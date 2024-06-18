package dsm.pick2024.domain.application.port.out

import java.util.*

interface ExistsApplicationPort {

    fun existsByUserId(userId: UUID): Boolean

    fun existsOKByUserId(userId: UUID): Boolean
}
