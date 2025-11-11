package dsm.pick2024.domain.afterschool.mapper

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent
import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity
import dsm.pick2024.domain.user.entity.UserJpaEntity
import dsm.pick2024.global.base.GenericMapper
import org.springframework.stereotype.Component

@Component
class AfterSchoolStudentMapper : GenericMapper<AfterSchoolStudentJpaEntity, AfterSchoolStudent> {

    fun toEntity(domain: AfterSchoolStudent, user: UserJpaEntity): AfterSchoolStudentJpaEntity =
        domain.run {
            AfterSchoolStudentJpaEntity(
                id = id,
                user = user,
                grade = grade,
                classNum = classNum,
                num = num,
                userName = userName,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }

    override fun toDomain(entity: AfterSchoolStudentJpaEntity): AfterSchoolStudent =
        entity.run {
            AfterSchoolStudent(
                id = id!!,
                userId = user.id!!,
                grade = grade,
                classNum = classNum,
                num = num,
                userName = userName,
                status1 = status1,
                status2 = status2,
                status3 = status3
            )
        }

    override fun toEntity(domain: AfterSchoolStudent): AfterSchoolStudentJpaEntity {
        throw UnsupportedOperationException("UserJpaEntity는 반드시 명시적으로 전달되어야 합니다.")
    }
}
