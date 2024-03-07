package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.exception.SelfStudyNotFoundException
import dsm.pick2024.domain.selfstudy.port.`in`.ChangeSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyByDateAndFloor
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySaveAllPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeSelfStudyTeacherService(
    private val selfStudySaveAllPort: SelfStudySaveAllPort,
    private val selfStudyByDate: SelfStudyByDateAndFloor
) : ChangeSelfStudyTeacherUseCase {

    @Transactional
    override fun changeSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val teacher = mutableListOf<SelfStudy>()
        request.teacher.forEach {
                requests ->
            val selfStudy = selfStudyByDate.findByDate(requests.date, requests.floor)
                ?: throw SelfStudyNotFoundException

            val update = selfStudy.copy(
                teacher = requests.teacher
            )
            teacher.add(update)
        }
        selfStudySaveAllPort.saveAll(teacher)
    }
}
