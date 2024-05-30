package dsm.pick2024.domain.application.port.out

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.domain.TempApplicationModel

interface SaveAllApplicationPort {
    fun saveAll(application: List<TempApplicationModel>)
    // 아래쪽 구현은 안함
}
