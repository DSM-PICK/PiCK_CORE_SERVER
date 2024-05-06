package dsm.pick2024.domain.timetable.port.`in`

import dsm.pick2024.domain.timetable.presentation.dto.request.ChangeTimetableRequest

interface ChangeTimetableUseCase {
    fun change(request: ChangeTimetableRequest)
}
