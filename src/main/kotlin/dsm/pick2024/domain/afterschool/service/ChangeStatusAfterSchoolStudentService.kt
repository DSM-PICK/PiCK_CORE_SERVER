package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.port.`in`.ChangeStatusAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.FindAfterSchoolStudentByUserIdPort
import dsm.pick2024.domain.afterschool.port.out.SaveAllAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.ChangeAfterSchoolStatusRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeStatusAfterSchoolStudentService(
    private val saveAllAfterSchoolStudentPort: SaveAllAfterSchoolStudentPort,
    private val findAfterSchoolStudentByUserIdPort: FindAfterSchoolStudentByUserIdPort
) : ChangeStatusAfterSchoolStudentUseCase {
    @Transactional
    override fun changeStatusAfterSchoolStudent(request: ChangeAfterSchoolStatusRequest) {
        val update = mutableListOf<AfterSchoolStudent>()

        request.list.map {
                requests ->
            val period = requests.period
            requests.afterSchool.map {
                    it ->
                val afterSchool = findAfterSchoolStudentByUserIdPort.findByUserId(it.userId)
                    ?: throw UserNotFoundException
                val add = when (period) {
                    8 -> afterSchool.copy(status1 = it.status)
                    9 -> afterSchool.copy(status2 = it.status)
                    10 -> afterSchool.copy(status3 = it.status)
                    else -> throw Exception("adsf")
                }
                update.add(add)
            }
            saveAllAfterSchoolStudentPort.saveAll(update)
        }
    }
}
