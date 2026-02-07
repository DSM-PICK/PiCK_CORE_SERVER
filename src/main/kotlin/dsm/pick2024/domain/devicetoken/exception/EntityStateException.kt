package dsm.pick2024.domain.devicetoken.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object EntityStateException : PickException(
    ErrorCode.ENTITY_STATE_EXCEPTION
)
