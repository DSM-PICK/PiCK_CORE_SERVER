package dsm.pick2024.domain.afterschool.mapper

import dsm.pick2024.domain.afterschool.domain.AfterSchool
import dsm.pick2024.domain.afterschool.entity.AfterSchoolJpaEntity
import dsm.pick2024.global.base.GenericMapper

class AfterSchoolMapper : GenericMapper<AfterSchoolJpaEntity, AfterSchool>{
    override fun toEntity(domain: AfterSchool) = domain.run {
        AfterSchoolJpaEntity(
            id = id,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
        )
    }

    override fun toDomain(entity: AfterSchoolJpaEntity) = entity.run {
        AfterSchool(
            id = id,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
        )
    }
}
