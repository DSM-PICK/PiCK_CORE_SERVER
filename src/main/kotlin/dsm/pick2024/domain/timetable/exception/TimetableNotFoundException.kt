package dsm.pick2024.domain.timetable.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object TimetableNotFoundException : PickException(
    ErrorCode.TIMETABLE_NOT_FOUND
)
