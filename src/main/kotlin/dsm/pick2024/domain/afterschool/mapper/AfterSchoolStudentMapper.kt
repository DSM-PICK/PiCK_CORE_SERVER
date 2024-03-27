package dsm.pick2024.domain.afterschool.mapper

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AfterSchoolStudentMapper : GenericMapper<AfterSchoolStudentJpaEntity, AfterSchoolStudent> {
    override fun toEntity(domain: AfterSchoolStudent) =
        domain.run {
            AfterSchoolStudentJpaEntity(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                num = num,
                name = name,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }

    override fun toDomain(entity: AfterSchoolStudentJpaEntity) =
        entity.run {
            AfterSchoolStudent(
                id = id,
                userId = userId,
                grade = grade,
                classNum = classNum,
                num = num,
                name = name,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }
}
