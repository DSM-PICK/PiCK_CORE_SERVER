package dsm.pick2024.domain.afterschool.persistence

import dsm.pick2024.domain.afterschool.domain.AfterSchool
import dsm.pick2024.domain.afterschool.mapper.AfterSchoolMapper
import dsm.pick2024.domain.afterschool.persistence.repository.AfterSchoolRepository
import dsm.pick2024.domain.afterschool.port.out.AfterSchoolPort
import org.springframework.stereotype.Component

@Component
class AfterSchoolPersistenceAdapter(
    private val afterSchoolMapper: AfterSchoolMapper,
    private val afterSchoolRepository: AfterSchoolRepository
): AfterSchoolPort {

    override fun save(afterSchool: AfterSchool) {
        afterSchoolRepository.save(afterSchoolMapper.toEntity(afterSchool))
    }
}
