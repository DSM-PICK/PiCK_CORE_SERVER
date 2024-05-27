package dsm.pick2024.domain.classroom.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.enums.Status
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

    override fun existsByUserId(userId: UUID): Boolean {
        return classroomRepository.existsByUserId(userId)
    }

    override fun findAll() = classroomRepository.findAll().map { classroomMapper.toDomain(it) }

    override fun deleteAll() {
        classroomRepository.deleteAll()
    }

    override fun saveAll(classroom: List<Classroom>) {
        val entities = classroom.map { classroomMapper.toEntity(it) }
        classroomRepository.saveAll(entities)
    }

    override fun findOKClassroom(id: UUID) =
        classroomRepository.findByUserIdAndStatus(id, Status.OK).let {
            classroomMapper.toDomain(it)
        }

    override fun queryFloorClassroom(floor: Int) =
        jpaQueryFactory
            .selectFrom(QClassroomJpaEntity.classroomJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QClassroomJpaEntity.classroomJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QClassroomJpaEntity.classroomJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QClassroomJpaEntity.classroomJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            )
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
            .orderBy(
                QClassroomJpaEntity.classroomJpaEntity.classNum.asc(),
                QClassroomJpaEntity.classroomJpaEntity.num.asc()
            )
            .fetch()
            .map { classroomMapper.toDomain(it) }

    override fun queryGradeClassroom(
        grade: Int,
        classNum: Int
    ) = jpaQueryFactory
        .selectFrom(QClassroomJpaEntity.classroomJpaEntity)
        .innerJoin(QUserJpaEntity.userJpaEntity)
        .on(
            QClassroomJpaEntity.classroomJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                .and(QClassroomJpaEntity.classroomJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                .and(QClassroomJpaEntity.classroomJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
        ).where(
            QUserJpaEntity.userJpaEntity.grade.eq(grade),
            QUserJpaEntity.userJpaEntity.classNum.eq(classNum)
        )
        .fetch()
        .map { classroomMapper.toDomain(it) }

    override fun existOKByUserId(userId: UUID): Boolean {
        val classroom = QClassroomJpaEntity.classroomJpaEntity
        val user = QUserJpaEntity.userJpaEntity

        return jpaQueryFactory.selectFrom(classroom)
            .innerJoin(user).on(classroom.userName.eq(user.name))
            .where(user.id.eq(userId), classroom.status.eq(Status.OK))
            .fetchFirst() != null
    }
}
