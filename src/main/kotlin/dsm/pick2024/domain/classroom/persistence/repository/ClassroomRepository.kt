package dsm.pick2024.domain.classroom.persistence.repository

import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.classroom.entity.ClassroomJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClassroomRepository : JpaRepository<ClassroomJpaEntity, UUID> {
    fun findByUserId(userId: UUID): ClassroomJpaEntity

    fun existsByUserId(userId: UUID): Boolean

    fun deleteByUserId(userId: UUID)

    fun findByUserIdAndStatus(
        id: UUID,
        status: Status
    ): ClassroomJpaEntity
}
