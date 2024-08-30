package dsm.pick2024.domain.weekendmeal.service

import dsm.pick2024.domain.weekendmeal.domain.WeekendMeal
import dsm.pick2024.domain.weekendmeal.enums.Status
import dsm.pick2024.domain.weekendmeal.enums.Status.NO
import dsm.pick2024.domain.weekendmeal.enums.Status.OK
import dsm.pick2024.domain.weekendmeal.port.`in`.PrintExcelWeekendMealUseCase
import dsm.pick2024.domain.weekendmeal.port.out.QueryWeekendMealPort
import java.time.LocalDate
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
import kotlin.time.times
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress

@Service
class PrintAllExcelWeekendMealService(
    private val weekendMealPort: QueryWeekendMealPort
) : PrintExcelWeekendMealUseCase {
    @Transactional(readOnly = true)
    override fun execute(response: HttpServletResponse) {
        val month = LocalDate.now().monthValue + 1
        val workbook: Workbook = XSSFWorkbook()

        // 주말급식 정보
        val userList: List<WeekendMeal> = weekendMealPort.findAll().sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )

        // 반별 그룹화
        val groupedByGradeClass = userList.groupBy { it.grade to it.classNum }

        val sheet: Sheet = workbook.createSheet("주말급식-신청명단").apply {
            // 기본 열 너비 설정 (반별로 4개의 열)
            val columnWidths = arrayOf(10, 10, 10, 15)
            (0 until 16).forEach { index ->
                setColumnWidth(index, columnWidths[index % 4] * 256)
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
            setFont(
                workbook.createFont().apply {
                    color = IndexedColors.BLACK.index
                    bold = true
                }
            )
        }

        // Body Style
        val bodyCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            alignment = HorizontalAlignment.CENTER
            verticalAlignment = VerticalAlignment.CENTER
        }

        val titleRow: Row = sheet.createRow(0)
        val titleCell = titleRow.createCell(0)
        titleCell.cellStyle = titleCellStyle
        titleCell.setCellValue("$month 월 주말급식 신청자 명단")
        sheet.addMergedRegion(CellRangeAddress(0, 0, 0, 15))

        // 데이터가 작성될 행 번호
        var nowRow = 1

        // 학년별로 반복
        (1..3).forEach { grade ->
            // Header 작성 (반별로 가로로 배치)
            val headerRow: Row = sheet.createRow(nowRow)
            (1..4).forEach { classNum ->
                val headerNames = listOf("학번", "이름", "주말급식", "금액")
                headerNames.forEachIndexed { index, header ->
                    val cell = headerRow.createCell((classNum - 1) * 4 + index)
                    cell.setCellValue(header)
                    cell.cellStyle = headerCellStyle
                }
            }

            // 학년 별 행 이동
            nowRow++

            // 최대 학생 수(학년 별 한 반의)
            val maxClassSize = (1..4).map { classNum ->
                groupedByGradeClass[grade to classNum]?.size ?: 0
            }.maxOrNull() ?: 0

            // 반별 데이터 배치
            (0 until maxClassSize).forEach { index ->
                val row: Row = sheet.createRow(nowRow + index)

                (1..4).forEach { classNum ->
                    val users = groupedByGradeClass[grade to classNum] ?: emptyList()

                    if (index < users.size) {
                        val user = users[index]
                        row.createCell((classNum - 1) * 4)
                            .setCellValue(user.grade * 1000 + user.classNum * 100 + user.num.toDouble())
                        row.createCell((classNum - 1) * 4 + 1).setCellValue(user.userName)
                        row.createCell((classNum - 1) * 4 + 2).setCellValue(statusChange(user.status)?.toString() ?: "")
                        row.createCell((classNum - 1) * 4 + 3).setCellValue("-")

                        row.forEach { cell ->
                            cell.cellStyle = bodyCellStyle
                        }
                    }
                }
            }

            // 다음 학년으로 넘어가기 전에 행 띄우기
            nowRow += maxClassSize + 1
        }

        // 파일 생성
        val fileName = "weekendMeal.xlsx"
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        response.setHeader("Content-Disposition", "attachment;filename=$fileName")

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

    private fun statusChange(status: Status): Int? {
        return when (status) {
            OK -> 1
            NO -> null
        }
    }
}
