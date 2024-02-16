package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.FindAfterSchoolStudentByIdPort
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfetSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.collections.List

@Service
class ChangeStatusAfterSchoolStudentService(
    private val saveAllAfetSchoolStudentPort: SaveAllAfetSchoolStudentPort,
    private val findAfterSchoolStudentByIdPort: FindAfterSchoolStudentByIdPort
) : ChangeStatusAfterSchoolStudentUseCase {

    @Transactional
    override fun changeStatusAfterSchoolStudent(request: List<ChangeAfterSchoolStatusRequest>) {
        val update = mutableListOf<AfterSchoolStudent>()
        for (requests in request) {
            val afterSchoolStudent = findAfterSchoolStudentByIdPort.findById(requests.id)
                ?: throw RuntimeException("")

            val updateAfterSchoolStudent = afterSchoolStudent.copy(
                status = requests.status
            )
            update.add(updateAfterSchoolStudent)
        }
        saveAllAfetSchoolStudentPort.saveAll(update)
    }
}
