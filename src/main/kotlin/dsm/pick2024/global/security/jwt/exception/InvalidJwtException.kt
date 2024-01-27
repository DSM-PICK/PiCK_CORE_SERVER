package dsm.pick2024.global.security.jwt.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object InvalidJwtException : PickException(
    ErrorCode.INVALID_TOKEN
)
