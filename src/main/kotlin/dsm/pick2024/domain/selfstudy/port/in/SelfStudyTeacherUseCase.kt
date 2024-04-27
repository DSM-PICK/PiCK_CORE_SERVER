package dsm.pick2024.domain.selfstudy.port.`in`

import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest

interface SelfStudyTeacherUseCase {
    fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest)
}
