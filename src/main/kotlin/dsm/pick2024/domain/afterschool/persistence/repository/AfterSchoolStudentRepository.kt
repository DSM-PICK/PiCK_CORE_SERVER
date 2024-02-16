package dsm.pick2024.domain.afterschool.persistence.repository

import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AfterSchoolStudentRepository : CrudRepository<AfterSchoolStudentJpaEntity, UUID>
