package dsm.pick2024.domain.classroom.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ClassroomNotFoundException : PickException(
    ErrorCode.CLASSROOM_NOT_FOUND
)
