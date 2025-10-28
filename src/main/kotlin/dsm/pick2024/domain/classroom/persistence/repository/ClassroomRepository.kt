package dsm.pick2024.domain.classroom.persistence.repository

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.entity.ClassroomJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClassroomRepository : JpaRepository<ClassroomJpaEntity, UUID> {
    fun findByUser_Id(userId: UUID): ClassroomJpaEntity

    fun existsByUser_Id(userId: UUID): Boolean

    fun existsByStatusAndUser_Id(status: Status, userId: UUID): Boolean

    fun deleteByUser_Id(userId: UUID)

    fun findByUser_IdAndStatus(
        id: UUID,
        status: Status
    ): ClassroomJpaEntity

    fun findAllByStatus(status: Status): List<ClassroomJpaEntity>
}
