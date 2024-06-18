package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.`in`.SaveTimetableUseCase
import dsm.pick2024.domain.timetable.port.out.SaveTimetablePort
import dsm.pick2024.infrastructure.feign.NeisTimetableFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveTimetableService(
    private val saveAllTimetablePort: SaveTimetablePort,
    private val neisTimetableFeignClientService: NeisTimetableFeignClientService
) : SaveTimetableUseCase {

    @Transactional
    override fun saveTimetable() {
        val timetableEntities = neisTimetableFeignClientService.getNeisInfoToEntity()

        timetableEntities?.let { saveAllTimetablePort.saveAll(it) }
    }
}
