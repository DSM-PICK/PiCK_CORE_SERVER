package dsm.pick2024.domain.user.presentation.dto.request

import org.springframework.web.multipart.MultipartFile

data class UpdateUserClubFromExcelRequest(
    val excel: MultipartFile
)
