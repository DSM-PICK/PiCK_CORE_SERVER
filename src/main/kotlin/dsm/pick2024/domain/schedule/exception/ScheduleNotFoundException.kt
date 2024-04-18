package dsm.pick2024.domain.schedule.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ScheduleNotFoundException : PickException(
    ErrorCode.SCHEDULE_NOT_FOUND
)
