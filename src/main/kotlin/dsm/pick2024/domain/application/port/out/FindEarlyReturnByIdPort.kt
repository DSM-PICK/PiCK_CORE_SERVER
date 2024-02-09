package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.EarlyReturn
import java.util.UUID

interface FindEarlyReturnByIdPort {
    fun findById(earlyReturnId: UUID): EarlyReturn?
}
