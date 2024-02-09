package dsm.pick2024.domain.classroom.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.entity.QClassroomJpaEntity
import dsm.pick2024.domain.classroom.mapper.ClassroomMapper
import dsm.pick2024.domain.classroom.persistence.repository.ClassroomRepository
import dsm.pick2024.domain.classroom.port.out.ClassroomPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component

@Component
class ClassroomPersistence(
    private val classroomMapper: ClassroomMapper,
    private val classroomRepository: ClassroomRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : ClassroomPort {
    override fun save(classroom: Classroom) {
        classroomRepository.save(classroomMapper.toEntity(classroom))
    }

    override fun deleteByUsername(username: String) {
        classroomRepository.deleteByUsername(username)
    }

    override fun findByUsername(username: String) =
        classroomRepository.findByUsername(username).let { classroomMapper.toDomain(it) }

    override fun existsByUsername(username: String): Boolean? {
        return classroomRepository.existsByUsername(username)
    }

    override fun queryFloorClassroom(floor: Int)  =
        jpaQueryFactory
            .select(QClassroomJpaEntity.classroomJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(QClassroomJpaEntity.classroomJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
            .where(
                QUserJpaEntity.userJpaEntity.grade.eq(
                    when (floor) {
                        4 -> 1
                        3 -> 2
                        2 -> 3
                        else -> throw IllegalArgumentException("Invalid floor number")
                    }
                ),
            )
            .fetch()
            .map { classroomMapper.toDomain(it) }
}
