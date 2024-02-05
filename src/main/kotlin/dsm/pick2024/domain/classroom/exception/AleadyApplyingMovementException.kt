package dsm.pick2024.domain.classroom.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object AleadyApplyingMovementException : PickException(
    ErrorCode.ALREADY_APPLYING_MOVEMENT
)
