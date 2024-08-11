package dsm.pick2024.domain.attendance.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object PeriodNotFoundException : PickException(
    ErrorCode.PERIOD_NOT_FOUND
)
