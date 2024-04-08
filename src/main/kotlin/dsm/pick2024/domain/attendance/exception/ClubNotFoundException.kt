package dsm.pick2024.domain.earlyreturn.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ClubNotFoundException : PickException(
    ErrorCode.CLUB_NOT_FOUND
)
