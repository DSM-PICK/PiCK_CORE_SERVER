package dsm.pick2024.infrastructure.zxing.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.infrastructure.zxing.entity.ApplicationQRCode
import dsm.pick2024.infrastructure.zxing.repository.ApplicationQRCodeRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import javax.imageio.ImageIO

@Service
class ApplicationQRCodeService(
    private val applicationQRCodeRepository: ApplicationQRCodeRepository
): GenerateApplicationQRCodeUseCase {

    override fun generateApplicationQRCode(username: String, teacherName: String, startTime: LocalTime, endTime: LocalTime, reason: String) {
        val width = 200
        val height = 200

        val byteArrayOutputStream = ByteArrayOutputStream()

        val message = "username=$username&teacherName=$teacherName&startTime=$startTime"

        val code = QRCodeWriter().encode(
            message,
            BarcodeFormat.QR_CODE,
            width,
            height,
            mapOf(EncodeHintType.MARGIN to 1)
        )

        ImageIO.write(MatrixToImageWriter.toBufferedImage(code), "png", byteArrayOutputStream)

        val qrCode = ApplicationQRCode(username, teacherName, startTime, endTime, reason, byteArrayOutputStream.toByteArray())
        applicationQRCodeRepository.save(qrCode)
    }
}
