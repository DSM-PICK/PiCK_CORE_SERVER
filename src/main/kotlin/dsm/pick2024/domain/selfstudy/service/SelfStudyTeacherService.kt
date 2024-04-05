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
            request.teacher.map { teacherRequest ->
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
        val teacherList = mutableListOf<SelfStudy>()

        request.teacher.forEach { requestedTeacher ->
            val existingSelfStudy = selfStudy.find { it!!.floor == requestedTeacher.floor }

            val modifiedSelfStudy =
                existingSelfStudy?.copy(teacher = requestedTeacher.teacher)
                    ?: SelfStudy(
                        floor = requestedTeacher.floor,
                        teacher = requestedTeacher.teacher,
                        date = request.date
                    )

            teacherList.add(modifiedSelfStudy)
        }

        selfStudySaveAllPort.saveAll(teacherList)
    }
}
