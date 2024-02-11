package dsm.pick2024.infrastructure.schedule

import dsm.pick2024.domain.application.port.out.DeleteAllApplicationPort
import dsm.pick2024.domain.classroom.port.out.DeleteAllClassRoomPort
import dsm.pick2024.domain.earlyreturn.port.out.DeleteAllEarlyReturnPort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduleService(
    private val deleteAllClassRoomPort: DeleteAllClassRoomPort,
    private val deleteAllEarlyReturnPort: DeleteAllEarlyReturnPort,
    private val deleteAllApplicationPort: DeleteAllApplicationPort
) {

    @Scheduled(cron = "0 30 20 * * ?", zone = "Asia/Seoul")
    fun deleteTable() {
        deleteAllClassRoomPort.deleteAll()
        deleteAllEarlyReturnPort.deleteAll()
        deleteAllApplicationPort.deleteAll()
    }
}
