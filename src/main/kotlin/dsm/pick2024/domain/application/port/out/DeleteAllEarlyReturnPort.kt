package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.EarlyReturn

interface DeleteAllEarlyReturnPort {
    fun deleteAll(earlyReturn: List<EarlyReturn>)
}
