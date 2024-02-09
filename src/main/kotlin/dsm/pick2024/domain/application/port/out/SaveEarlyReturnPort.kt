package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.EarlyReturn

interface SaveEarlyReturnPort {
    fun saveAll(earlyReturn: List<EarlyReturn>)
}
