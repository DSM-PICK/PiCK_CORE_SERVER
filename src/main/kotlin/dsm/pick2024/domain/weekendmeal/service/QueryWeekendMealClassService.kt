package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealClassUseCase
import dsm.pick2024.domain.weekendmeal.port.`in`.WeekendMealFinderUseCase
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QueryWeekendMealClassService(
    private val weekendMealFinderUseCase: WeekendMealFinderUseCase
) : QueryWeekendMealClassUseCase {

    override fun queryWeekendMealClass(
        grade: Int,
        classNum: Int
    ) = weekendMealFinderUseCase.findByGradeAndClassNumOrThrow(grade, classNum)
        .map {
            QueryWeekendMealResponse(
                id = it.id,
                userName = it.userName,
                status = it.status,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num
            )
        }.distinctBy { it.id }
}
