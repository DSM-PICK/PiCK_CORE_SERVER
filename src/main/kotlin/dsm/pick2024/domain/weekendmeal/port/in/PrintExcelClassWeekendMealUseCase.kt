package dsm.pick2024.domain.weekendmeal.port.`in`

import javax.servlet.http.HttpServletResponse

interface PrintExcelClassWeekendMealUseCase {
    fun execute(response: HttpServletResponse, grade: Int, classNum: Int)
}
