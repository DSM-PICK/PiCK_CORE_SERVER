package dsm.pick2024.domain.classroom.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object MoveRequiredOnClubDayException : PickException(
    ErrorCode.MOVE_REQUIRED_ON_CLUB_DAY
)
