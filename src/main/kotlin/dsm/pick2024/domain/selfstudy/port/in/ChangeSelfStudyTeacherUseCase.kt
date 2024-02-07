package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest

interface ChangeSelfStudyTeacherUseCase {
    fun changeSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest)
}
