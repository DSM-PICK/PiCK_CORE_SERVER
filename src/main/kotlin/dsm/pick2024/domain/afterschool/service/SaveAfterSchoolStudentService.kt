package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import dsm.pick2024.domain.user.port.out.FindByNamePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolStudentService(
    private val saveAllAfterSchoolStudentPort: SaveAllAfterSchoolStudentPort,
    private val findByNamePort: FindByNamePort
) : SaveAfterSchoolStudentUseCase {
    @Transactional
    override fun saveAfterSchoolStudentUseCase(request: List<SaveAfterSchoolStudentRequest>) {
        val afterSchoolStudent =
            request.map { requests ->
                val user = findByNamePort.findByName(requests.name)
                AfterSchoolStudent(
                    grade = requests.grade,
                    classNum = requests.classNum,
                    num = requests.num,
                    name = user!!.name,
                    status1 = Status.ATTENDANCE,
                    status2 = Status.ATTENDANCE,
                    status3 = Status.ATTENDANCE
                )
            }
        saveAllAfterSchoolStudentPort.saveAll(afterSchoolStudent)
    }
}
