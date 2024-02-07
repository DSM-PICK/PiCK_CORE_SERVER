package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.port.`in`.RegistrationSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySavePort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegistrationSelfStudyTeacherService(
    private val selfStudySavePort: SelfStudySavePort
) : RegistrationSelfStudyTeacherUseCase {

    @Transactional
    override fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        selfStudySavePort.save(
            SelfStudy(
                teacher = request.teacher,
                floor = request.floor,
                date = request.date
            )
        )
    }
}
