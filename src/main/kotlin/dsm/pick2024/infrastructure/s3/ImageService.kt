package dsm.pick2024.infrastructure.s3

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val fileUtil: FileUtil
) {
    fun uploadImage(file: MultipartFile, path: String) =
        fileUtil.upload(file, path)
}
