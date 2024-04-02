package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface QueryAllEarlyReturnByStatusPort {
    fun findAllByStatus(status: Status): List<EarlyReturn>
}
