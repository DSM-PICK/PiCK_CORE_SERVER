package dsm.pick2024.domain.selfstudy.persistence

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.mapper.SelfStudyMapper
import dsm.pick2024.domain.selfstudy.persistence.repository.SelfStudyRepository
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyPortAndFloor
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class SelfStudyPersistenceAdapterAndFloor (
    private val selfStudyRepository: SelfStudyRepository,
    private val selfStudyMapper: SelfStudyMapper
): SelfStudyPortAndFloor {

    override fun save(selfStudy: SelfStudy) {
        selfStudyRepository.save(selfStudyMapper.toEntity(selfStudy))
    }

    override fun findByDate(date: LocalDate, floor: Int) =
        selfStudyRepository.findByDateAndFloor(date, floor).let { selfStudyMapper.toDomain(it) }
}
