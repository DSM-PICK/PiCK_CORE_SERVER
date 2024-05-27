package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.port.`in`.QueryWeekendMealClassUseCase
import dsm.pick2024.domain.weekendmeal.port.out.FindWeekendMealClassPort
import dsm.pick2024.domain.weekendmeal.port.out.FindWeekendMealQuitClassPort
import dsm.pick2024.domain.weekendmeal.presentation.dto.response.QueryWeekendMealResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class QueryWeekendMealClassService(
    private val findWeekendMealClassPort: FindWeekendMealClassPort,
    private val findWeekendMealQuitClassPort: FindWeekendMealQuitClassPort
) : QueryWeekendMealClassUseCase {

    override fun queryWeekendMealClass(
        grade: Int,
        classNum: Int
    ) = findWeekendMealClassPort.findByGradeAndClassNum(grade, classNum)
        .map {
                it ->
            QueryWeekendMealResponse(
                id = it.id!!,
                name = it.userName,
                status = it.status,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num
            )
        }

    override fun queryWeekendMealQuitClass(
        grade: Int,
        classNum: Int
    ) = findWeekendMealQuitClassPort.findQuitByGradeAndClassNum(grade, classNum)
        .map {
                it ->
            QueryWeekendMealResponse(
                it.id!!,
                it.userName,
                it.status,
                it.grade,
                it.classNum,
                it.num
            )
        }
}
