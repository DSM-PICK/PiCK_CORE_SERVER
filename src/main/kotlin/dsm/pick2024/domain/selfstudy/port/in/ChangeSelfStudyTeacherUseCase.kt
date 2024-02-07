package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.request.ChangeSelfStudyTeacherRequest

interface ChangeSelfStudyTeacherUseCase {
    fun changeSelfStudyTeacher(request: ChangeSelfStudyTeacherRequest)
}
