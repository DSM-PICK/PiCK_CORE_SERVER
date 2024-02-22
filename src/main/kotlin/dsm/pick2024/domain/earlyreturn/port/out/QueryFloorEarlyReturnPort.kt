package dsm.pick2024.domain.earlyreturn.port.out

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn

interface QueryFloorEarlyReturnPort {
    fun findByFloor(floor: Int): List<EarlyReturn>
}
