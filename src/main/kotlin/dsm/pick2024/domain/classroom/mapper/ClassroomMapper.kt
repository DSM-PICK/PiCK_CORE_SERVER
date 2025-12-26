package dsm.pick2024.domain.classroom.mapper

import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.entity.ClassroomJpaEntity
import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class ClassroomMapper : GenericMapper<ClassroomJpaEntity, Classroom> {
    override fun toEntity(domain: Classroom): ClassroomJpaEntity {
        TODO("Not yet implemented")
    }
    fun toEntity(domain: Classroom, user: UserJpaEntity) =
        domain.run {
            ClassroomJpaEntity(
                id = id,
                user = user,
                classroomName = classroomName,
                floor = floor,
                userName = userName,
                grade = grade,
                classNum = classNum,
                num = num,
                startPeriod = startPeriod,
                endPeriod = endPeriod,
                status = status,
                move = move
            )
        }

    override fun toDomain(entity: ClassroomJpaEntity) =
        entity.run {
            Classroom(
                id = id!!,
                userId = user.id!!,
                classroomName = classroomName,
                floor = floor,
                userName = userName,
                grade = grade,
                classNum = classNum,
                num = num,
                startPeriod = startPeriod,
                endPeriod = endPeriod,
                status = status,
                move = move
            )
        }
}
