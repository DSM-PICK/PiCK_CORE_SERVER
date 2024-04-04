package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.port.`in`.RegistrationSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.FindByDatePort
import dsm.pick2024.domain.selfstudy.port.out.SelfStudySaveAllPort
import dsm.pick2024.domain.selfstudy.presentation.dto.request.RegistrationSelfStudyTeacherRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class RegistrationSelfStudyTeacherService(
    private val selfStudySaveAllPort: SelfStudySaveAllPort,
    private val findByDatePort: FindByDatePort
) : RegistrationSelfStudyTeacherUseCase {

    override fun registrationSelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val teacherList = request.teacher.map { teacherRequest ->
            SelfStudy(
                date = request.date,
                floor = teacherRequest.floor,
                teacher = teacherRequest.teacher
            )
        }
        selfStudySaveAllPort.saveAll(teacherList)
    }

    override fun modifySelfStudyTeacher(request: RegistrationSelfStudyTeacherRequest) {
        val selfStudy = findByDatePort.findByDateList(request.date)

        val modifiedSelfStudies = selfStudy.mapNotNull { existeSelfStudy ->
            request.teacher.find { it.teacher == existeSelfStudy!!.teacher }?.let { teacherRequest ->
                existeSelfStudy!!.copy(teacher = request.teacher.toString())
            }
        }

        selfStudySaveAllPort.saveAll(modifiedSelfStudies)
    }
}
