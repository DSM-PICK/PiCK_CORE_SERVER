package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.domain.EarlyReturn

interface QueryFloorEarlyReturnPort {
    fun findByFloor(floor: Int): List<EarlyReturn>
}
