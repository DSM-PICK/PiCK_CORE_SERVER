package dsm.pick2024.domain.schedule.port.`in`

interface ScheduleUseCase {
    fun saveNeisInfoToDatabase(start: String, end: String)
}
