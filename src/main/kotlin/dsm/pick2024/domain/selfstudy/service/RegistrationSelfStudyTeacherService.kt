package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.port.`in`.RegistrationSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.ExistsByDateAndFloor
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySaveAllPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegistrationSelfStudyTeacherService(
    private val selfStudySaveAllPort: SelfStudySaveAllPort,
    private val existsByDateAndFloor: ExistsByDateAndFloor
) : RegistrationSelfStudyTeacherUseCase {

    @Transactional
    override fun registrationSelfStudyTeacher(request: List<RegistrationSelfStudyTeacherRequest>) {
        val teacher = mutableListOf<SelfStudy>()

        request.forEach {
                requests ->
            if (existsByDateAndFloor.existsByDateAndFloor(requests.date, requests.floor) == true) {
                throw RuntimeException("자습 감독 선생님이 이미 등록되어 있습니다.")
            }

            teacher.add(
                SelfStudy(
                    date = requests.date,
                    floor = requests.floor,
                    teacher = requests.teacher
                )
            )
        }
        selfStudySaveAllPort.saveAll(teacher)
    }
}
