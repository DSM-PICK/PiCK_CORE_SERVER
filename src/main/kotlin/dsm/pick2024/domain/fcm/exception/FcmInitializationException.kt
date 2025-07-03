package dsm.pick2024.domain.fcm.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object FcmInitializationException : PickException(
    ErrorCode.FCM_INITIALIZATION_ERROR
)
