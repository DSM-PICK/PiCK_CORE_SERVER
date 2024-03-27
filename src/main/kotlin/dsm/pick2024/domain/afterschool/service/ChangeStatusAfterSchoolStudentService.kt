package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.FindAfterSchoolStudentByUserIdPort
import dsm.pick2024.domain.afterschool.port.out.SaveAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.List

@Service
class ChangeStatusAfterSchoolStudentService(
    private val saveAfterSchoolStudentPort: SaveAfterSchoolStudentPort,
    private val findAfterSchoolStudentByUserIdPort: FindAfterSchoolStudentByUserIdPort
) : ChangeStatusAfterSchoolStudentUseCase {
    @Transactional
    override fun changeStatusAfterSchoolStudent(request: List<ChangeAfterSchoolStatusRequest>) {
        request.forEach { changeRequest ->
            val student =
                findAfterSchoolStudentByUserIdPort.findByUserId(changeRequest.id)
                    ?: throw UserNotFoundException

            val newStatusList = changeRequest.statusList
            val updatedStudent =
                student.copy(
                    status1 = newStatusList.getOrElse(0) { student.status1 },
                    status2 = newStatusList.getOrElse(1) { student.status2 },
                    status3 = newStatusList.getOrElse(2) { student.status3 }
                )

            saveAfterSchoolStudentPort.save(updatedStudent)
        }
    }
}
