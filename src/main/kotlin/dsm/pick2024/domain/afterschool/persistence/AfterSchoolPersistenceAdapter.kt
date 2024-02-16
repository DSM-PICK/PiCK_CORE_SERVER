package dsm.pick2024.domain.afterschool.persistence

import dsm.pick2024.domain.afterschool.mapper.AfterSchoolMapper
import dsm.pick2024.domain.afterschool.persistence.repository.AfterSchoolRepository
import org.springframework.stereotype.Component

@Component
class AfterSchoolPersistenceAdapter(
    private val afterSchoolMapper: AfterSchoolMapper,
    private val afterSchoolRepository: AfterSchoolRepository
) {
}
