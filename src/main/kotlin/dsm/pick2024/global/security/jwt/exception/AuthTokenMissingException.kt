package dsm.pick2024.global.security.jwt.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object AuthTokenMissingException : PickException(
    ErrorCode.AUTH_TOKEN_MISSING
)
