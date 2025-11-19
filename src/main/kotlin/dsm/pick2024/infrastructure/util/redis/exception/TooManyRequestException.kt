package dsm.pick2024.infrastructure.util.redis.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object TooManyRequestException : PickException(
    ErrorCode.TOO_MANY_REQUEST
)
