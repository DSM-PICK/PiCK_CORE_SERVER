package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.port.out.ExistsByAdminIdPort
import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.exception.TeacherNotFoundException
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.DeleteSelfStudyPort
import dsm.pick2024.domain.selfstudy.port.out.QuerySelfStudyPort
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SelfStudyTeacherService(
    private val selfStudyPort: SelfStudyPort,
    private val querySelfStudyPort: QuerySelfStudyPort,
    private val deleteSelfStudyPort: DeleteSelfStudyPort,
    private val existsByAdminIdPort: ExistsByAdminIdPort

) : SelfStudyTeacherUseCase {

    override fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val selfStudy = querySelfStudyPort.findByDateList(request.date)

        if (selfStudy.isNotEmpty()) deleteSelfStudyPort.deleteByDate(request.date)

        val teacherList =
            request.teacher
                .filter { it.teacher.isNotBlank() }
                .mapNotNull { teacher ->
                    val exist = selfStudy.find { it!!.floor == teacher.floor }
                    exist?.copy(teacherName = teacher.teacher) ?: SelfStudy(
                        floor = teacher.floor,
                        teacherName = teacher.teacher,
                        date = request.date
                    )
                }

        teacherList.forEach {
            if (!existsByAdminIdPort.existByName(it.teacherName)) {
                throw TeacherNotFoundException
            }
        }

        selfStudyPort.saveAll(teacherList)
    }
}
