package dsm.pick2024.domain.application.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object AlreadyApplyingForPicnicException : PickException(
    ErrorCode.ALREADY_APPLYING_PICNIC,
)
