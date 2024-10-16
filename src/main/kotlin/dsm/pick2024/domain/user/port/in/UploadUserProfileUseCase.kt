package dsm.pick2024.domain.user.port.`in`

import org.springframework.web.multipart.MultipartFile

interface UploadUserProfileUseCase {
    fun uploadUserProfile(file: MultipartFile)
}
