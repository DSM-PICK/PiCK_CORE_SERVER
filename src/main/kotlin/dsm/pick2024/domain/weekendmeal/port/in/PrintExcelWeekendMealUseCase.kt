package dsm.pick2024.domain.weekendmeal.port.`in`

import javax.servlet.http.HttpServletResponse

interface PrintExcelWeekendMealUseCase {
    fun execute(response: HttpServletResponse)
}
