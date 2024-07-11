package dsm.pick2024.domain.bug

import dsm.pick2024.infrastructure.s3.FileUtil
import dsm.pick2024.infrastructure.s3.PathList
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UploadBugImageService(
    private val fileUtil: FileUtil
) {
    fun uploadBugImage(file: List<MultipartFile>) =
        file.map {
            fileUtil.upload(it, PathList.BUG)
        }
}
