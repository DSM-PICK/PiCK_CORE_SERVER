package dsm.pick2024.domain.user.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object DuplicateUserException: PickException(
    ErrorCode.DUPLICATE_USER,
) {
}
