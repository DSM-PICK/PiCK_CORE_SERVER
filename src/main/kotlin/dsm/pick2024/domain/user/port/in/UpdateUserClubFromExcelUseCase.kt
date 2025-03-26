package dsm.pick2024.domain.user.port.`in`

import org.springframework.web.multipart.MultipartFile

interface UpdateUserClubFromExcelUseCase {
    fun updateUserClubFromExcel(file : MultipartFile)
}
