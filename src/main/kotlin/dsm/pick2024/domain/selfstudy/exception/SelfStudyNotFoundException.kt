package dsm.pick2024.domain.selfstudy.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object SelfStudyNotFoundException : PickException(
    ErrorCode.SELF_STUDY_NOT_FOUND
)
