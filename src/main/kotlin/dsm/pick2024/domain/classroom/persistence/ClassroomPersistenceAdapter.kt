package dsm.pick2024.domain.classroom.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.entity.QClassroomJpaEntity
import dsm.pick2024.domain.classroom.mapper.ClassroomMapper
import dsm.pick2024.domain.classroom.persistence.repository.ClassroomRepository
import dsm.pick2024.domain.classroom.port.out.ClassroomPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ClassroomPersistenceAdapter(
    private val classroomMapper: ClassroomMapper,
    private val classroomRepository: ClassroomRepository,
    private val jpaQueryFactory: JPAQueryFactory
) : ClassroomPort {
    override fun save(classroom: Classroom) {
        classroomRepository.save(classroomMapper.toEntity(classroom))
    }

    override fun deleteByUserId(userId: UUID) {
        classroomRepository.deleteByUserId(userId)
    }

    override fun findByUserId(userId: UUID) =
        classroomRepository.findByUserId(userId).let { classroomMapper.toDomain(it) }

    override fun existsByUserId(userId: UUID): Boolean? {
        return classroomRepository.existsByUserId(userId)
    }

    override fun findAll() = classroomRepository.findAll().map { classroomMapper.toDomain(it) }

    override fun deleteAll() {
        classroomRepository.deleteAll()
    }

    override fun queryFloorClassroom(floor: Int) =
        jpaQueryFactory
            .selectFrom(QClassroomJpaEntity.classroomJpaEntity)
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
                )
            )
            .fetch()
            .map { classroomMapper.toDomain(it) }

    override fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QClassroomJpaEntity.classroomJpaEntity)
        .innerJoin(QUserJpaEntity.userJpaEntity)
        .on(QClassroomJpaEntity.classroomJpaEntity.username.eq(QUserJpaEntity.userJpaEntity.name))
        .where(
            QUserJpaEntity.userJpaEntity.grade.eq(grade),
            QUserJpaEntity.userJpaEntity.classNum.eq(classNum)
        )
        .fetch()
        .map { classroomMapper.toDomain(it) }
}
