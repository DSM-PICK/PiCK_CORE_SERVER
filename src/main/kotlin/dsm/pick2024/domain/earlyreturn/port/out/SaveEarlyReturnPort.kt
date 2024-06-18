package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface SaveEarlyReturnPort {
    fun save(earlyReturn: EarlyReturn)

    fun saveAll(earlyReturn: List<EarlyReturn>)
}
