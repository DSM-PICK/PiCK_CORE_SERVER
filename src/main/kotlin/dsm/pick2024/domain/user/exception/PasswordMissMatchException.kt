package dsm.pick2024.domain.user.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object PasswordMissMatchException : PickException(
    ErrorCode.PASSWORD_MISS_MATCH
)
