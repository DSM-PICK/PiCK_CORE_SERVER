package dsm.pick2024.global.security.jwt.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ExpiredTokenException : PickException(
    ErrorCode.EXPIRED_TOKEN
)
