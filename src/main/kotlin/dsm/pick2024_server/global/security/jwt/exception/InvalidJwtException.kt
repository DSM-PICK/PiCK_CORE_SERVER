package dsm.pick2024_server.global.security.jwt.exception

import dsm.pick2024_server.global.error.exception.ErrorCode
import dsm.pick2024_server.global.error.exception.PickException

object InvalidJwtException : PickException(
    ErrorCode.INVALID_TOKEN
)
