package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.enums.Status
import dsm.pick2024.domain.afterschool.port.`in`.SaveAllAfterSchoolUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfterSchoolStudentPort
import dsm.pick2024.infrastructure.feign.client.XquareFeignClient
import org.springframework.stereotype.Service

@Service
class SaveAllAfterSchoolService(
    private val xquareFeignClient: XquareFeignClient,
    private val saveAllAfterSchoolStudentPort: SaveAllAfterSchoolStudentPort
) : SaveAllAfterSchoolUseCase {
    override fun saveAll(key: String) {
        val xquareUserInfo = xquareFeignClient.userAll(key)
        val afterSchoolStudents =
            xquareUserInfo.map { user ->
                AfterSchoolStudent(
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    name = user.name,
                    status1 = Status.ATTENDANCE,
                    status2 = Status.ATTENDANCE,
                    status3 = Status.ATTENDANCE
                )
            }
        saveAllAfterSchoolStudentPort.saveAll(afterSchoolStudents)
    }
}
