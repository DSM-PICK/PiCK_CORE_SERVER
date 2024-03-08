package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.FindAfterSchoolStudentByIdPort
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfetSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.collections.List

@Service
class ChangeStatusAfterSchoolStudentService(
    private val saveAllAfetSchoolStudentPort: SaveAllAfetSchoolStudentPort,
    private val findAfterSchoolStudentByIdPort: FindAfterSchoolStudentByIdPort
) : ChangeStatusAfterSchoolStudentUseCase {
    @Transactional
    override fun changeStatusAfterSchoolStudent(request: List<ChangeAfterSchoolStatusRequest>) {
        val updates =
            request.map { changeRequest ->
                val student = findAfterSchoolStudentByIdPort.findById(changeRequest.id) ?: throw RuntimeException()

                val new = changeRequest.statusList
                student!!.copy(
                    status1 = new.getOrElse(0) { student.status1 },
                    status2 = new.getOrElse(1) { student.status2 },
                    status3 = new.getOrElse(2) { student.status3 }
                )
            }

        saveAllAfetSchoolStudentPort.saveAll(updates)
    }
}
