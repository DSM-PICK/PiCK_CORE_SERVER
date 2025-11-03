package dsm.pick2024.domain.classroom.finder

import dsm.pick2024.domain.classroom.exception.ClassroomNotFoundException
import dsm.pick2024.domain.classroom.port.`in`.ClassroomFinderUseCase
import dsm.pick2024.domain.classroom.port.out.QueryClassroomPort
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClassroomFinder(
    private val queryClassroomPort: QueryClassroomPort
) : ClassroomFinderUseCase {
    override fun findByUserIdOrThrow(userId: UUID) =
        queryClassroomPort.findByUserId(userId) ?: throw ClassroomNotFoundException

    override fun findOKClassroomOrThrow(id: UUID) =
        queryClassroomPort.findOKClassroom(id) ?: throw ClassroomNotFoundException
}
