package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface SaveAllEarlyReturnPort {
    fun saveAll(earlyReturn: List<EarlyReturn>)
}
