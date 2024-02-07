package dsm.pick2024.domain.selfstudy.mapper

import dsm.pick2024.domain.selfstudy.domain.SelfStudy
import dsm.pick2024.domain.selfstudy.entity.SelfStudyJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class SelfStudyMapper : GenericMapper<SelfStudyJpaEntity, SelfStudy>{

    override fun toEntity(domain: SelfStudy) = domain.run {
        SelfStudyJpaEntity(
            id = id,
            floor = floor,
            teacher = teacher,
            date = date
        )
    }

    override fun toDomain(entity: SelfStudyJpaEntity?) = entity?.run {
        SelfStudy(
            id = id,
            floor = floor,
            teacher = teacher,
            date = date
        )
    }
}
