package dsm.pick2024.domain.timetable.service

import dsm.pick2024.domain.timetable.port.out.SaveAllTimetablePort
import dsm.pick2024.infrastructure.feign.NeisTimetableFeignClientService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveTimetableService(
    private val saveAllTimetablePort: SaveAllTimetablePort,
    private val neisTimetableFeignClientService: NeisTimetableFeignClientService
) {

    @Transactional
    fun saveTimetable() {
        val timetableEntities = neisTimetableFeignClientService.getNeisInfoToEntity()

        timetableEntities?.let { saveAllTimetablePort.saveAll(it) }
    }
}
