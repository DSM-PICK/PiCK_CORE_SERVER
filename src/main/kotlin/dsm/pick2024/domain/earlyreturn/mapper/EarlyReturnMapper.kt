package dsm.pick2024.domain.earlyreturn.mapper

import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.entity.EarlyReturnJpaEntity
import org.springframework.stereotype.Component

@Component
class EarlyReturnMapper {
    fun toEntity(domain: EarlyReturn) = domain.run {
        EarlyReturnJpaEntity(
            id = id,
            reason = reason,
            startTime = startTime,
            userName = userName,
            status = status,
            teacherName = teacherName,
            date = date,
            grade = grade,
            classNum = classNum,
            num = num,
            userId = userId
        )
    }

    fun toDomain(entity: EarlyReturnJpaEntity) = entity.run {
        EarlyReturn(
            id = id!!,
            reason = reason,
            startTime = startTime,
            userName = userName,
            status = status,
            teacherName = teacherName,
            date = date, grade = grade,
            classNum = classNum,
            num = num,
            userId = userId
        )
    }
}
