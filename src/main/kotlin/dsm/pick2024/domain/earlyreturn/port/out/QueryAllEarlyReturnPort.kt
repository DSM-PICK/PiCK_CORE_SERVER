package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface QueryAllEarlyReturnPort {
    fun findAll(): List<EarlyReturn>
}
