package dsm.pick2024.domain.afterschool.mapper

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import dsm.pick2024.domain.user.mapper.UserMapper
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AfterSchoolStudentMapper(
    private val userMapper: UserMapper
) : GenericMapper<AfterSchoolStudentJpaEntity, AfterSchoolStudent> {
    override fun toEntity(domain: AfterSchoolStudent): AfterSchoolStudentJpaEntity {
        return domain.run {
            AfterSchoolStudentJpaEntity(
                id = id,
                user = userMapper.toEntity(user),
                grade = grade,
                classNum = classNum,
                num = num,
                userName = userName,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }
    }

    override fun toDomain(entity: AfterSchoolStudentJpaEntity) =
        entity.run {
            AfterSchoolStudent(
                id = id!!,
                user = userMapper.toDomain(user),
                grade = grade,
                classNum = classNum,
                num = num,
                userName = userName,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }
}
