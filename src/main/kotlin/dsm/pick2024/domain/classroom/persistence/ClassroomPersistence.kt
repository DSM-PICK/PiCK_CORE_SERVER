package dsm.pick2024.domain.classroom.persistence

import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.mapper.ClassroomMapper
import dsm.pick2024.domain.classroom.persistence.repository.ClassroomRepository
import dsm.pick2024.domain.classroom.port.out.ClassroomPort
import org.springframework.stereotype.Component

@Component
class ClassroomPersistence(
    private val classroomMapper: ClassroomMapper,
    private val classroomRepository: ClassroomRepository
) : ClassroomPort {
    override fun save(classroom: Classroom) {
        classroomRepository.save(classroomMapper.toEntity(classroom))
    }
}
