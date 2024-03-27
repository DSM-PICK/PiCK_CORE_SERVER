package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfterSchoolStudentPort
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolStudentService(
    private val saveAllAfterSchoolStudentPort: SaveAllAfterSchoolStudentPort,
    private val xquareFeignClient: XquareFeignClient
) : SaveAfterSchoolStudentUseCase {
    @Transactional
    override fun saveAfterSchoolStudent(key: String) {
        val xquareUserInfo = xquareFeignClient.userAll(key)

        val afterSchoolStudent =
            xquareUserInfo.map { user ->
                AfterSchoolStudentJpaEntity(
                    id = null,
                    userId = user.id,
                    name = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    status1 = Status.ATTENDANCE,
                    status2 = Status.ATTENDANCE,
                    status3 = Status.ATTENDANCE
                )
            }.toMutableList()
        saveAllAfterSchoolStudentPort.saveAll(afterSchoolStudent)
    }
}
