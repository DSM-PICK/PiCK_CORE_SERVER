package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchool
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAfterSchoolPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolService(
    private val saveAfterSchoolPort: SaveAfterSchoolPort
): SaveAfterSchoolUseCase {

    @Transactional
    override fun saveAfterSchoolUseCase(request: SaveAfterSchoolRequest) {
        saveAfterSchoolPort.save(
            AfterSchool(
                grade = request.grade,
                classNum = request.classNum,
                num = request.num,
                name = request.name
            )
        )
    }
}
