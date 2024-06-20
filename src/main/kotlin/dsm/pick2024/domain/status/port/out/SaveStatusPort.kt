package dsm.pick2024.domain.status.port.out

import dsm.pick2024.domain.status.domain.Status

interface SaveStatusPort {
    fun save(status: Status)

    fun saveAll(statuses: MutableList<Status>)
}
