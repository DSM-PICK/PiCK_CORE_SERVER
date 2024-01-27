package dsm.pick2024.global.security.jwt.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object InternalServerErrorException : PickException(
    ErrorCode.INTERNAL_SERVER_ERROR
)
