package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.port.`in`.DeleteAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.AfterSchoolStudentPortUser
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DeleteAfterSchoolStudentService(
    private val deleteAfterSchoolStudentPort: AfterSchoolStudentPortUser
) : DeleteAfterSchoolStudentUseCase {
    @Transactional
    override fun deleteAfterSchoolStudent(id: UUID) = deleteAfterSchoolStudentPort.deleteById(id)
}
