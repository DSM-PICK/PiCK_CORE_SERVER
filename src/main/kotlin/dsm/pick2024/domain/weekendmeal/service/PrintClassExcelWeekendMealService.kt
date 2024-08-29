package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.enums.Status.NO
import dsm.pick2024.domain.weekendmeal.enums.Status.OK
import dsm.pick2024.domain.weekendmeal.port.`in`.PrintExcelClassWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PrintClassExcelWeekendMealService(
    private val queryWeekendMealPort: QueryWeekendMealPort
) : PrintExcelClassWeekendMealUseCase {

    @Transactional(readOnly = true)
    override fun execute(response: HttpServletResponse, grade: Int, classNum: Int) {
        val month = LocalDate.now().monthValue + 1
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("주말급식-신청명단").apply {
            defaultColumnWidth = 15
            val columnWidths = arrayOf(5, 5, 5, 10, 20, 30)
            columnWidths.forEachIndexed { index, width ->
                setColumnWidth(index, width * 256)
            }
        }

        // Title Style
        val titleCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.NONE)
            fillForegroundColor = IndexedColors.WHITE.index
            alignment = HorizontalAlignment.CENTER
            setFont(
                workbook.createFont().apply {
                    color = IndexedColors.BLACK.index
                    fontHeightInPoints = 20
                }
            )
        }

        // Header Style
        val headerCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            fillForegroundColor = IndexedColors.GREY_25_PERCENT.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            alignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
            wrapText = true
            setFont(workbook.createFont().apply { color = IndexedColors.BLACK.index })
        }

        // Body Style
        val bodyCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            alignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
        }

        // teacher Row
        val teacherStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.NONE)
            alignment = HorizontalAlignment.RIGHT
            verticalAlignment = VerticalAlignment.CENTER
        }

        // Title Row
        val titleRowIndex = 0
        val titleRow: Row = sheet.createRow(titleRowIndex)
        val titleCell = titleRow.createCell(0)
        titleCell.setCellValue("$month 월 주말급식 신청서")
        titleCell.cellStyle = titleCellStyle
        sheet.addMergedRegion(CellRangeAddress(titleRowIndex, titleRowIndex, 0, 5))

        // Explain Row
        val explainRowIndex = 1
        val explainRow: Row = sheet.createRow(explainRowIndex)
        val explainCell = explainRow.createCell(0)
        explainCell.setCellValue("급식 먹는 학생: 신청에 \"1\" 표시")
        explainCell.cellStyle = bodyCellStyle
        sheet.addMergedRegion(CellRangeAddress(explainRowIndex, explainRowIndex, 0, 5))

        val teacherRowIndex = 2
        val teacherRow: Row = sheet.createRow(teacherRowIndex)
        val teacherCell = teacherRow.createCell(4)
        teacherCell.setCellValue("담임")
        teacherCell.cellStyle = teacherStyle

        // Header Row
        val headerNames = arrayOf(
            "학년",
            "반",
            "번호",
            "이름",
            "주말급식 신청여부\n\"1\"",
            "급식 관련 기타 특이사항\n(예: 휴학, 병결, 파견 등)"
        )

        val headerRow: Row = sheet.createRow(3)
        headerNames.forEachIndexed { i, header ->
            val cell = headerRow.createCell(i)
            cell.setCellValue(header)
            cell.cellStyle = headerCellStyle
        }

        // Body Rows
        val userList: List<WeekendMeal> =
            queryWeekendMealPort.findByGradeAndClassNum(grade, classNum).sortedBy { it.num }

        userList.forEachIndexed { index, user ->
            val bodyRow: Row = sheet.createRow(index + 4)
            bodyRow.createCell(0).setCellValue(user.grade.toDouble())
            bodyRow.createCell(1).setCellValue(user.classNum.toDouble())
            bodyRow.createCell(2).setCellValue(user.num.toDouble())
            bodyRow.createCell(3).setCellValue(user.userName)
            bodyRow.createCell(4).setCellValue(statusChange(user.status)?.toString())
            bodyRow.createCell(5)
            bodyRow.forEach { cell ->
                cell.cellStyle = bodyCellStyle
            }
        }

        // Summary Row
        val summaryRowIndex = userList.size + 4
        sheet.addMergedRegion(CellRangeAddress(summaryRowIndex, summaryRowIndex, 0, 3))
        val summaryRow = sheet.createRow(summaryRowIndex)
        val summaryCell = summaryRow.createCell(0)
        summaryCell.setCellValue("합계")
        summaryCell.cellStyle = bodyCellStyle
        summaryRow.createCell(4).setCellValue(
            queryWeekendMealPort.findByGradeAndClassNumAndStatus(grade, classNum, OK)
                ?.count().toString()
        )
        summaryRow.createCell(5).setCellValue("-")

        (1..5).forEach { colIndex ->
            summaryRow.getCell(colIndex)?.cellStyle = bodyCellStyle
        }

        // File
        val fileName = "weekendMeal_$grade-$classNum.xlsx"
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        response.setHeader("Content-Disposition", "attachment;filename=$fileName")

        try {
            workbook.write(response.outputStream)
            response.outputStream.flush()
        } finally {
            workbook.close()
            response.outputStream.close()
        }
    }

    private fun CellStyle.setBorderStyle(style: BorderStyle) {
        borderLeft = style
        borderRight = style
        borderTop = style
        borderBottom = style
    }

    private fun statusChange(status: Status): Int? {
        return when (status) {
            OK -> 1
            NO -> null
        }
    }
}
