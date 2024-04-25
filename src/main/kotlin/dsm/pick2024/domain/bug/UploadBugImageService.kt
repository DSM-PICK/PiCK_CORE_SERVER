package dsm.pick2024.domain.bug

import dsm.pick2024.infrastructure.s3.ImageService
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadBugImageService(
    private val imageService: ImageService
) {
    fun uploadBugImage(file: MultipartFile) =
        imageService.uploadImage(file, PathList.BUG)
}
