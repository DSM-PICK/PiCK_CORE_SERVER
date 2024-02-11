package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface DeleteAllEarlyReturnPort {
    fun deleteAll(earlyReturn: List<EarlyReturn>)
}
