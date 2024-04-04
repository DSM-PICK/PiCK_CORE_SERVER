package dsm.pick2024.domain.main

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object MainException : PickException(
    ErrorCode.MAIN_NOT_FOUND
)
