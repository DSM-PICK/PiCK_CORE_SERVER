package dsm.pick2024.domain.selfstudy.exception

import dsm.pick2024.global.error.exception.ErrorCode
import dsm.pick2024.global.error.exception.PickException

object ExistsSelfStudyTeacherException : PickException(
    ErrorCode.EXISTS_SELF_STUDY_TEACHER
)
