package dsm.pick2024.domain.classroom.mapper

import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.entity.ClassroomJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class ClassroomMapper : GenericMapper<ClassroomJpaEntity, Classroom> {

    override fun toEntity(domain: Classroom) = domain.run {
        ClassroomJpaEntity(
            id = id,
            classroomName = classroomName,
            floor = floor,
            username = username,
            move = move,
            grade = grade,
            classNum = classNum,
            num = num
        )
    }

    override fun toDomain(entity: ClassroomJpaEntity?) = entity?.run {
        Classroom(
            id = id,
            classroomName = classroomName,
            floor = floor,
            username = username,
            move = move,
            grade = grade,
            classNum = classNum,
            num = num
        )
    }
}
