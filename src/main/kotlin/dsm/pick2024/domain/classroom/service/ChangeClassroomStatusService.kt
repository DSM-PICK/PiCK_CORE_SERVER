package dsm.pick2024.domain.classroom.service

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.exception.ClassroomNorFoundException
import dsm.pick2024.domain.classroom.port.`in`.ChangeClassroomStatusUseCase
import dsm.pick2024.domain.classroom.port.out.ClassroomDeletePort
import dsm.pick2024.domain.classroom.port.out.FindByUserIdPort
import dsm.pick2024.domain.classroom.port.out.SaveAllClassroomPort
import dsm.pick2024.domain.classroom.presentation.dto.request.ClassroomStatusRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ChangeClassroomStatusService(
    private val findByUserIdPort: FindByUserIdPort,
    private val classroomDeletePort: ClassroomDeletePort,
    private val saveAllClassroomPort: SaveAllClassroomPort
) : ChangeClassroomStatusUseCase {
    @Transactional
    override fun changeClassroomStatus(request: ClassroomStatusRequest) {
        val update = mutableListOf<Classroom>()

        if (request!!.status == Status.NO) {
            for (id in request.ids) {
                val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException
                classroomDeletePort.deleteByUserId(classroom.userId!!)
            }
            return
        }

        for (id in request.ids) {
            val classroom = findByUserIdPort.findByUserId(id) ?: throw ClassroomNorFoundException

            val updatedApplication =
                classroom.copy(
                    status = Status.OK
                )
            update.add(updatedApplication)
        }

        saveAllClassroomPort.saveAll(update)
    }
}
