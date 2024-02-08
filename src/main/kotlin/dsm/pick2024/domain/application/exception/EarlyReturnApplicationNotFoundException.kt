package dsm.pick2024.domain.application.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object EarlyReturnApplicationNotFoundException : PickException(
    ErrorCode.EARLY_RETURN_NOT_FOUND
)
