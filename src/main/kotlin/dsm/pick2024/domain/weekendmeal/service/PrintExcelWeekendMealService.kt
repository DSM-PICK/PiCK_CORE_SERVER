package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.persistence.WeekendMealPersistenceAdapter
import dsm.pick2024.domain.weekendmeal.port.`in`.PrintExcelWeekendMealUseCase
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.servlet.http.HttpServletResponse

@Service
class PrintExcelWeekendMealService(
    private val weekendMealPersistenceAdapter: WeekendMealPersistenceAdapter
) : PrintExcelWeekendMealUseCase {

    @Transactional(readOnly = true)
    override fun execute(response: HttpServletResponse) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet =
            workbook.createSheet("weekendMeal_apply").apply {
                defaultColumnWidth = 30
            }

        // Header
        val headerCellStyle: CellStyle =
            workbook.createCellStyle().apply {
                setBorderStyle(BorderStyle.THIN)
                fillForegroundColor = IndexedColors.BLACK1.index
                fillPattern = FillPatternType.SOLID_FOREGROUND
                setFont(
                    workbook.createFont().apply {
                        color = IndexedColors.WHITE.index
                    }
                )
            }

        val headerNames = arrayOf("이름", "학년", "반", "번호")

        val headerRow: Row = sheet.createRow(0)
        headerNames.forEachIndexed { i, header ->
            headerRow.createCell(i).apply {
                setCellValue(header)
                cellStyle = headerCellStyle
            }
        }

        // Body
        val bodyCellStyle: CellStyle =
            workbook.createCellStyle().apply {
                setBorderStyle(BorderStyle.THIN)
            }

        val userList: List<WeekendMeal> = weekendMealPersistenceAdapter.findByStatus(Status.OK)

        userList.forEachIndexed { index, user ->
            val bodyRow: Row = sheet.createRow(index + 1)
            bodyRow.createCell(0).setCellValue(user.userName)
            bodyRow.createCell(1).setCellValue(user.grade.toDouble())
            bodyRow.createCell(2).setCellValue(user.classNum.toDouble())
            bodyRow.createCell(3).setCellValue(user.num.toDouble())
            bodyRow.forEach { cell ->
                cell.cellStyle = bodyCellStyle
            }
        }

        // File
        val fileName = "weekendMeal_apply.spreadsheet_download"
        response.contentType = "application/weekendMeal_apply.spreadsheet.sheet"
        response.setHeader("Content-Disposition", "attachment;filename=$fileName.xlsx")
        val servletOutputStream = response.outputStream

        workbook.write(servletOutputStream)
        workbook.close()
        servletOutputStream.flush()
        servletOutputStream.close()
    }

    private fun CellStyle.setBorderStyle(style: BorderStyle) {
        borderLeft = style
        borderRight = style
        borderTop = style
        borderBottom = style
    }
}
