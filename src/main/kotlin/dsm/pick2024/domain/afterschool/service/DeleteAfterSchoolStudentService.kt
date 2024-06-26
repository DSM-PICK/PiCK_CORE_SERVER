package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.DeleteAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.DeleteAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.DeleteRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteAfterSchoolStudentService(
    private val deleteAfterSchoolStudentPort: DeleteAfterSchoolStudentPort
) : DeleteAfterSchoolStudentUseCase {

    @Transactional
    override fun deleteAfterSchoolStudent(request: DeleteRequest) =
        deleteAfterSchoolStudentPort.deleteById(request.id)
}
