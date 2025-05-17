package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.attendance.port.out.QueryAttendancePort
import dsm.pick2024.domain.attendance.port.out.SaveAttendancePort
import dsm.pick2024.domain.user.exception.NotExtensionXslException
import dsm.pick2024.domain.user.port.`in`.UpdateUserClubFromExcelUseCase
import org.apache.commons.compress.utils.FileNameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import javax.transaction.Transactional

@Service
class UpdateUserClubFromExcelService(
    private val queryAttendancePort: QueryAttendancePort,
    private val saveAttendancePort: SaveAttendancePort
) : UpdateUserClubFromExcelUseCase {
    val STUDENT_NUM_INDEX = 1
    val STUDENT_CULB_INDEX = 3
    val STUDENT_PLACE_INDEX = 6
    val STUDENT_FLOOR_INDEX = 7
    val START_DATA_INDEX = 2


    @Transactional
    override fun updateUserClubFromExcel(file: MultipartFile) {
        val extension = FileNameUtils.getExtension(file.originalFilename!!).lowercase()

        if (extension != "xls" && extension != "xlsx") {
            throw NotExtensionXslException
        }

        var excel: Workbook? = null
        try {
            if (extension == "xlsx") {
                excel = XSSFWorkbook(file.inputStream)
            } else {
                excel = HSSFWorkbook(file.inputStream)
            }

            val worksheet: Sheet = excel.getSheetAt(0)

            for (i in START_DATA_INDEX until worksheet.physicalNumberOfRows) {
                val row: Row = worksheet.getRow(i)
                val studentNum = row.getCell(STUDENT_NUM_INDEX).toString().trim()
                val clubName = row.getCell(STUDENT_CULB_INDEX).toString().trim()
                val floor = row.getCell(STUDENT_FLOOR_INDEX).toString().trim()
                val place = row.getCell(STUDENT_PLACE_INDEX).toString().trim()
                if (studentNum != "" && clubName != "" && floor != "") {
                    updateClub(
                        studentNum.substring(0, 1).toInt(),
                        studentNum.substring(1, 2).toInt(),
                        studentNum.substring(2, 4).toInt(),
                        clubName,
                        floor.substring(0, 1).toInt(),
                        place,
                    )
                }
            }
        } finally {
            excel?.close()
        }
    }

    private fun updateClub(grade: Int, classNum: Int, num: Int, clubName: String, floor: Int, place: String) {
        val attendance = queryAttendancePort.findByStudentNum(grade, classNum, num)
        attendance?.updateClub(clubName, floor, place)?.let { saveAttendancePort.save(it) }
    }
}
