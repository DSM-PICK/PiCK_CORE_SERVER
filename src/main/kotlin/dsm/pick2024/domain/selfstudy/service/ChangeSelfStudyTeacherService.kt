package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.exception.SelfStudyNotFoundException
import dsm.pick2024.domain.selfstudy.port.`in`.ChangeSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyByDateAndFloor
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySavePort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.ChangeSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeSelfStudyTeacherService(
    private val selfStudySavePort: SelfStudySavePort,
    private val selfStudyByDate: SelfStudyByDateAndFloor
) : ChangeSelfStudyTeacherUseCase {

    @Transactional
    override fun changeSelfStudyTeacher(request: ChangeSelfStudyTeacherRequest) {
        val selfStudy = selfStudyByDate.findByDate(request.date, request.floor)
            ?: throw SelfStudyNotFoundException

        val updatedSelfStudy = selfStudy.copy(
            teacher = request.teacher
        )

        selfStudySavePort.save(updatedSelfStudy)
    }
}

