package dsm.pick2024.domain.mail.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ExpiredMailCodeException : PickException(
    ErrorCode.EXPIRED_EMAIL_CODE
)
