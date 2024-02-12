package dsm.pick2024.infrastructure.s3

import com.amazonaws.HttpMethod
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import org.springframework.beans.factory.annotation.Value
import java.util.*

class S3Utils(
    private val amazonS3: AmazonS3,
    @Value("\${spring.cloud.aws.s3.bucket}")
    val bucketName: String
) {

    companion object {
        const val PATH: String = "QRCODE/"
    }
    fun generateObjectUrl(fileName: String) =
        amazonS3.generatePresignedUrl(
            GeneratePresignedUrlRequest(
                bucketName,
                "${PATH}$fileName"
            ).withMethod(HttpMethod.GET)
        ).toString()
}
