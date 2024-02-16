package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolStudentService(
    private val saveAfterSchoolStudentPort: SaveAfterSchoolStudentPort
): SaveAfterSchoolStudentUseCase {

    @Transactional
    override fun saveAfterSchoolStudentUseCase(request: SaveAfterSchoolStudentRequest) {
        saveAfterSchoolStudentPort.save(
            AfterSchoolStudent(
                grade = request.grade,
                classNum = request.classNum,
                num = request.num,
                name = request.name
            )
        )
    }
}
