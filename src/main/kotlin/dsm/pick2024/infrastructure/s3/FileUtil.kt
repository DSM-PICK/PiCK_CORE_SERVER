package dsm.pick2024.infrastructure.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import dsm.pick2024.infrastructure.s3.exception.BadFileExtensionException
import dsm.pick2024.infrastructure.s3.exception.EmptyFileException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*

@Service
class FileUtil(
    private val amazonS3: AmazonS3
) {
    @Value("\${cloud.aws.s3.bucket}")
    lateinit var bucketName: String

    companion object {
        const val EXP_TIME = 1000 * 60 * 60 * 24 * 7
    }

    fun upload(file: MultipartFile, path: String): String {
        val ext = verificationFile(file)

        val randomName = UUID.randomUUID().toString()
        val filename = "$randomName.$ext"
        val inputStream: InputStream = ByteArrayInputStream(file.bytes)

        val metadata = ObjectMetadata().apply {
            contentType = MediaType.IMAGE_PNG_VALUE
            contentLength = file.size
            contentDisposition = "inline"
        }

        inputStream.use {
            amazonS3.putObject(
                PutObjectRequest(bucketName, "${path}$filename", it, metadata)
                    .withCannedAcl(CannedAccessControlList.AuthenticatedRead)
            )
        }

        return filename
    }

    fun delete(objectName: String, path: String) {
        amazonS3.deleteObject(bucketName, path + objectName)
    }

    fun generateObjectUrl(fileName: String, path: String): String {
        val expiration = Date().apply {
            time += EXP_TIME
        }

        return amazonS3.generatePresignedUrl(
            GeneratePresignedUrlRequest(
                bucketName,
                "${path}$fileName"
            ).withMethod(HttpMethod.GET).withExpiration(expiration)
        ).toString()
    }

    private fun verificationFile(file: MultipartFile): String {
        if (file.isEmpty || file.originalFilename == null) throw EmptyFileException
        val originalFilename = file.originalFilename!!
        val ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).lowercase(Locale.getDefault())

        if (!(ext == "jpg" || ext == "jpeg" || ext == "png" || ext == "heic" || ext == "hwp" || ext == "pptx")) {
            throw BadFileExtensionException
        }

        return ext
    }
}
