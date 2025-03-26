package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.attendance.persistence.AttendancePersistenceAdapter
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
class UpdateUserClubFromExcelService (
    private val attendancePersistenceAdapter: AttendancePersistenceAdapter,
    private val saveAttendancePort: SaveAttendancePort
): UpdateUserClubFromExcelUseCase {
    @Transactional
    override fun updateUserClubFromExcel(file: MultipartFile) {
        val extension = FileNameUtils.getExtension(file.originalFilename!!)

        if(extension != "xls" && extension != "xlsx") {
            throw NotExtensionXslException
        }

        var excel: Workbook? = null

        if (extension == "xlsx") {
            excel = XSSFWorkbook(file.inputStream)
        } else{
            excel = HSSFWorkbook(file.inputStream)
        }

        val worksheet: Sheet = excel.getSheetAt(0)

        for(i in 2 until worksheet.physicalNumberOfRows){
            val row: Row = worksheet.getRow(i)
            val studentNum = row.getCell(0).toString().trim()
            val clubName = row.getCell(2).toString().trim()
            if(studentNum != "" && clubName != "") {
                updateClub(
                    studentNum.substring(0, 1).toInt(),
                    studentNum.substring(1, 2).toInt(),
                    studentNum.substring(2, 4).toInt(),
                    clubName
                )
            }
        }
    }

    private fun updateClub(grade:Int, classNum:Int, num:Int, clubName:String){
        val attendance = attendancePersistenceAdapter.findByStudentNum(grade, classNum, num)
        attendance?.updateClub(clubName)?.let { saveAttendancePort.save(it) }
    }

}
