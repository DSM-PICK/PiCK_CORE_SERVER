package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.EarlyReturn

interface SaveAllEarlyReturnPort {
    fun saveAll(earlyReturn: List<EarlyReturn>)
}
