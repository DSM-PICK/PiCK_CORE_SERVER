package dsm.pick2024.domain.afterschool.service

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.attendance.enums.AttendanceStatus
import dsm.pick2024.domain.afterschool.port.`in`.SaveAfterSchoolStudentUseCase
import dsm.pick2024.domain.afterschool.port.out.SaveAfterSchoolStudentPort
import dsm.pick2024.domain.afterschool.presentation.dto.request.SaveAfterSchoolStudentRequest
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.out.QueryUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAfterSchoolStudentService(
    private val saveAfterSchoolStudentPort: SaveAfterSchoolStudentPort,
    private val queryUserPort: QueryUserPort
) : SaveAfterSchoolStudentUseCase {

    @Transactional
    override fun saveAfterSchoolStudent(request: List<SaveAfterSchoolStudentRequest>) {
        val afterSchoolStudent =
            request.map { requests ->
                val (grade, classNum, num) = parseSchoolNum(requests.studentNum)
                val user = queryUserPort.findByStudentNum(grade, classNum, num)
                    ?: throw UserNotFoundException

                AfterSchoolStudent(
                    userId = user.id!!,
                    grade = grade,
                    classNum = classNum,
                    num = num,
                    userName = user.name,
                    status1 = AttendanceStatus.ATTENDANCE,
                    status2 = AttendanceStatus.ATTENDANCE,
                    status3 = AttendanceStatus.ATTENDANCE
                )
            }
        saveAfterSchoolStudentPort.saveAll(afterSchoolStudent)
    }

    private fun parseSchoolNum(schoolNum: String): Triple<Int, Int, Int> {
        val grade = schoolNum.substring(0, 1).toInt()
        val classNum = schoolNum.substring(1, 2).toInt()
        val num = schoolNum.substring(2).toInt()
        return Triple(grade, classNum, num)
    }
}
