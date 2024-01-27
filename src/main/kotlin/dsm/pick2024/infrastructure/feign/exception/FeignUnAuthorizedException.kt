package dsm.pick2024.infrastructure.feign.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object FeignUnAuthorizedException : PickException(
    ErrorCode.FEIGN_UNAUTHORIZED
)
