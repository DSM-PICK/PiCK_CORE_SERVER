package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application

interface QueryFloorApplicationPort {
    fun findByFloor(floor: Int): List<Application>
}
