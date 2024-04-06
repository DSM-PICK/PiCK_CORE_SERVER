package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.FindByDatePort
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySaveAllPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class SelfStudyTeacherService(
    private val selfStudySaveAllPort: SelfStudySaveAllPort,
    private val findByDatePort: FindByDatePort
) : SelfStudyTeacherUseCase {
    override fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val teacherList =
            request.teacher
                .filter { it.teacher.isNotBlank() }
                .map { teacher ->
                    SelfStudy(
                        floor = teacher.floor,
                        teacher = teacher.teacher,
                        date = request.date
                    )
                }
        selfStudySaveAllPort.saveAll(teacherList)
    }

    override fun modifySelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        if (request.teacher.any { it.teacher.isNotBlank() }) {
            val selfStudy = findByDatePort.findByDateList(request.date)
            val teacherList =
                request.teacher
                    .filter { it.teacher.isNotBlank() }
                    .mapNotNull { teacher ->
                        val exist = selfStudy.find { it!!.floor == teacher.floor }

                        exist?.copy(teacher = teacher.teacher) ?: SelfStudy(
                            floor = teacher.floor,
                            teacher = teacher.teacher,
                            date = request.date
                        )
                    }

            selfStudySaveAllPort.saveAll(teacherList)
        }
    }
}
