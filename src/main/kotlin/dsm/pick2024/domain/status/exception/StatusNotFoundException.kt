package dsm.pick2024.domain.status.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object StatusNotFoundException : PickException(
    ErrorCode.STATUS_NOT_FOUND
)
