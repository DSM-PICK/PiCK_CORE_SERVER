package dsm.pick2024.domain.classroom.persistence.repository

import dsm.pick2024.domain.classroom.entity.ClassroomJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ClassroomRepository : JpaRepository<ClassroomJpaEntity, UUID>
