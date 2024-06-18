package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface QueryAllEarlyReturnPort {
    fun findAll(): List<EarlyReturn>

    fun findAllByStatus(status: Status): List<EarlyReturn>
}
