package dsm.pick2024.domain.classroom.persistence

import com.querydsl.jpa.impl.JPAQueryFactory
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.classroom.domain.Classroom
import dsm.pick2024.domain.classroom.entity.QClassroomJpaEntity
import dsm.pick2024.domain.classroom.mapper.ClassroomMapper
import dsm.pick2024.domain.classroom.persistence.repository.ClassroomRepository
import dsm.pick2024.domain.classroom.port.out.ClassRoomPort
import dsm.pick2024.domain.user.entity.QUserJpaEntity
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PersistenceAdapterClassRoom(
    private val classroomMapper: ClassroomMapper,
    private val classroomRepository: ClassroomRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val attendancePort: QueryAttendancePort,
    private val userRepository: UserRepository
) : ClassRoomPort {
    override fun save(classroom: Classroom) {
        val user = userRepository.findById(classroom.userId) ?: throw UserNotFoundException
        classroomRepository.save(classroomMapper.toEntity(classroom, user))
    }

    override fun deleteByUserId(userId: UUID) {
        classroomRepository.deleteByUserId(userId)
    }

    override fun findByUserId(userId: UUID) =
        classroomRepository.findByUserId(userId).let { classroomMapper.toDomain(it) }

    override fun existsByUserId(userId: UUID) =
        classroomRepository.existsByUserId(userId)

    override fun existOKByUserId(userId: UUID) =
        classroomRepository.existsByStatusAndUserId(Status.OK, userId)

    override fun findAll() = classroomRepository.findAll().map { classroomMapper.toDomain(it) }

    override fun deleteAll() {
        classroomRepository.deleteAll()
    }

    override fun saveAll(classroom: List<Classroom>) {
        val entities = classroom.map {
            val user = userRepository.findById(it.userId) ?: throw UserNotFoundException
            classroomMapper.toEntity(it, user)
        }
        classroomRepository.saveAll(entities)
    }

    override fun findOKClassroom(id: UUID) =
        classroomRepository.findByUserIdAndStatus(id, Status.OK)?.let {
            classroomMapper.toDomain(it)
        }

    override fun findAllByStatus(status: Status): List<Classroom> =
        classroomRepository.findAllByStatus(status).map { classroomMapper.toDomain(it) }

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

    override fun queryFloorClassroomWithAttendance(floor: Int): List<Classroom> {
        val attendances = attendancePort.findByFloor(floor)
        val userIds = attendances?.map { it.userId }

        val classrooms = jpaQueryFactory
            .selectFrom(QClassroomJpaEntity.classroomJpaEntity)
            .innerJoin(QUserJpaEntity.userJpaEntity)
            .on(
                QClassroomJpaEntity.classroomJpaEntity.grade.eq(QUserJpaEntity.userJpaEntity.grade)
                    .and(QClassroomJpaEntity.classroomJpaEntity.classNum.eq(QUserJpaEntity.userJpaEntity.classNum))
                    .and(QClassroomJpaEntity.classroomJpaEntity.num.eq(QUserJpaEntity.userJpaEntity.num))
            )
            .where(
                QUserJpaEntity.userJpaEntity.id.`in`(userIds)
            )
            .fetch()

        return classrooms.map { classroomMapper.toDomain(it) }
    }
}
