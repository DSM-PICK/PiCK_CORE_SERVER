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
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress

@Service
class PrintAllExcelWeekendMealService(
    private val queryWeekendMealPort: QueryWeekendMealPort
) : PrintExcelWeekendMealUseCase {

    @Transactional(readOnly = true)
    override fun execute(response: HttpServletResponse) {
        val month = LocalDate.now().monthValue + 1
        val workbook: Workbook = XSSFWorkbook()

        // 주말급식 정보
        val userList: List<WeekendMeal> = queryWeekendMealPort.findAll().sortedWith(
            compareBy({ it.grade }, { it.classNum }, { it.num })
        )

        // 반별 그룹화
        val groupedByGradeClass = userList.groupBy { it.grade to it.classNum }

        // 주말급식 신청명단
        val mainSheet: Sheet = workbook.createSheet("주말급식-신청명단").apply {
            // 기본 열 너비 설정 (반별로 4개의 열)
            val columnWidths = arrayOf(10, 10, 15, 15)
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

        val titleRow: Row = mainSheet.createRow(0)
        val titleCell = titleRow.createCell(0)
        titleCell.cellStyle = titleCellStyle
        titleCell.setCellValue("${month}월 주말급식 신청자 명단")
        mainSheet.addMergedRegion(CellRangeAddress(0, 0, 0, 15))

        // 데이터가 작성될 행 번호
        var nowRow = 1

        // 학년별로 반복
        (1..3).forEach { grade ->
            val headerRow: Row = mainSheet.createRow(nowRow)
            (1..4).forEach { classNum ->
                val headers = listOf("학번", "이름", "주말급식", "금액")
                headers.forEachIndexed { index, header ->
                    val cell = headerRow.createCell((classNum - 1) * 4 + index)
                    cell.setCellValue(header)
                    cell.cellStyle = headerCellStyle
                }
            }

            nowRow++

            // 최대 학생 수(학년 별 한 반의)
            val maxClassSize = (1..4).map { classNum ->
                groupedByGradeClass[grade to classNum]?.size ?: 0
            }.maxOrNull() ?: 0

            (0 until maxClassSize).forEach { index ->
                val row: Row = mainSheet.createRow(nowRow + index)
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

            // 학년 별 행 이동
            nowRow += maxClassSize + 1
        }

        // 합계 테이블
        val summarySheet: Sheet = workbook.createSheet("합계").apply {
            defaultColumnWidth = 10
        }

        val headerName = listOf("학년", "인원", "총 금액")
        val startRow = 0
        val startCell = 0

        // Header 생성
        val headerRow = summarySheet.createRow(startRow)
        headerName.forEachIndexed { i, name ->
            headerRow.createCell(startCell + i).apply {
                setCellValue(name)
                cellStyle = headerCellStyle
            }
        }

        var totalCount = 0

        (1..3).forEachIndexed { i, grade ->
            val count = queryWeekendMealPort.findByStatus(OK).filter { it.grade == grade }.size
            summarySheet.createRow(startRow + i + 1).apply {
                createCell(startCell).apply {
                    setCellValue("${grade}학년")
                    cellStyle = bodyCellStyle
                }
                createCell(startCell + 1).apply {
                    setCellValue(count.toDouble())
                    cellStyle = bodyCellStyle
                }
                createCell(startCell + 2).apply {
                    setCellValue("-")
                    cellStyle = bodyCellStyle
                }
            }
            totalCount += count
        }

        // 합계 삽입
        summarySheet.createRow(startRow + 4).apply {
            val cell = 3
            createCell(startCell).apply {
                setCellValue("합계")
                cellStyle = headerCellStyle
            }
            createCell(startCell + 1).apply {
                setCellValue(totalCount.toDouble())
                cellStyle = headerCellStyle
            }
            createCell(startCell + 2).apply {
                setCellValue("-")
                cellStyle = headerCellStyle
            }
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
