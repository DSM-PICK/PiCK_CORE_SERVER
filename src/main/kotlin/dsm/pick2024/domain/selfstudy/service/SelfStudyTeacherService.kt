package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.DeleteByDatePort
import dsm.pick2024.domain.selfstudy.port.out.FindByDatePort
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySaveAllPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SelfStudyTeacherService(
    private val selfStudySaveAllPort: SelfStudySaveAllPort,
    private val findByDatePort: FindByDatePort,
    private val deleteByDatePort: DeleteByDatePort
) : SelfStudyTeacherUseCase {

    override fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val selfStudy = findByDatePort.findByDateList(request.date)

        if (selfStudy.isNotEmpty()) deleteByDatePort.deleteByDate(request.date)

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

        selfStudySaveAllPort.saveAll(teacherList)
    }
}
