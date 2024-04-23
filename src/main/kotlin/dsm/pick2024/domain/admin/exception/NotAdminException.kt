package dsm.pick2024.domain.admin.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object NotAdminException : PickException(
    ErrorCode.NOT_ADMIN
)
