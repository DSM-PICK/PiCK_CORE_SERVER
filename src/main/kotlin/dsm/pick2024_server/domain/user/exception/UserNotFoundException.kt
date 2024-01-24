package dsm.pick2024_server.domain.user.exception

import dsm.pick2024_server.global.error.exception.ErrorCode
import dsm.pick2024_server.global.error.exception.PickException

object UserNotFoundException: PickException (
    ErrorCode.USER_NOT_FOUND
)
