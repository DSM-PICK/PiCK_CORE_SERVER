package dsm.pick2024.domain.timetable.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object InvalidSubjectException : PickException(
    ErrorCode.INVALID_SUBJECT
)
