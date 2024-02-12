package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface DeleteAllEarlyReturnListPort {
    fun deleteAll(earlyReturn: List<EarlyReturn>)
}
