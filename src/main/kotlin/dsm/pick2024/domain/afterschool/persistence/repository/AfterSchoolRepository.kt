package dsm.pick2024.domain.afterschool.persistence.repository

import dsm.pick2024.domain.afterschool.entity.AfterSchoolJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AfterSchoolRepository : CrudRepository<AfterSchoolJpaEntity, UUID>
