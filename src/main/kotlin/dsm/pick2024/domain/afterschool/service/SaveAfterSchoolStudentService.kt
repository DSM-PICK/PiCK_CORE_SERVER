package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfetSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import dsm.pick2024.domain.user.port.`in`.UserFacadeUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolStudentService(
    private val saveAllAfetSchoolStudentPort: SaveAllAfetSchoolStudentPort,
    private val userFacadeUseCase: UserFacadeUseCase,
) : SaveAfterSchoolStudentUseCase {
    @Transactional
    override fun saveAfterSchoolStudentUseCase(request: List<SaveAfterSchoolStudentRequest>) {
        val afterSchoolStudent = request.map { requests ->
            val user = userFacadeUseCase.getUserByName(requests.name)
            AfterSchoolStudent(
                grade = requests.grade,
                classNum = requests.classNum,
                num = requests.num,
                name = user.name,
                status1 = Status.CHECK,
                status2 = Status.CHECK,
                status3 = Status.CHECK
            )
        }
        saveAllAfetSchoolStudentPort.saveAll(afterSchoolStudent)
    }
}
