package dsm.pick2024.domain.notice.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object NoticeNotFoundException : PickException(
    ErrorCode.NOTICE_NOT_FOUND
)
