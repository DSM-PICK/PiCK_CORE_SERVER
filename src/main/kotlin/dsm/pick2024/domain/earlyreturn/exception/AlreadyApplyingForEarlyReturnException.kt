package dsm.pick2024.domain.earlyreturn.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object AlreadyApplyingForEarlyReturnException : PickException(
    ErrorCode.ALREADY_APPLYING_EARLY_RETURN
)
