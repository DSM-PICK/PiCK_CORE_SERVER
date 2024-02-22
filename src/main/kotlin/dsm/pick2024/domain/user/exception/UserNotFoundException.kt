package dsm.pick2024.domain.user.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object UserNotFoundException : PickException(
    ErrorCode.USER_NOT_FOUND
)
