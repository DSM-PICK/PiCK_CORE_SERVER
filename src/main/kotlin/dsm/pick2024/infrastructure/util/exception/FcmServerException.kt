package dsm.pick2024.infrastructure.util.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object FcmServerException : PickException(
    ErrorCode.FCM_SERVER_ERROR
)
