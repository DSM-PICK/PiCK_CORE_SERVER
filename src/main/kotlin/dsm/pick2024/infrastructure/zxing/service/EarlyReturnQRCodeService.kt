package dsm.pick2024.infrastructure.zxing.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.infrastructure.zxing.entity.EarlyReturnQRCode
import dsm.pick2024.infrastructure.zxing.repository.EarlyReturnQRCodeRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import javax.imageio.ImageIO

@Service
class EarlyReturnQRCodeService(
    private val earlyReturnQRCodeRepository: EarlyReturnQRCodeRepository
): GenerateEarlyReturnQRCodeUseCase {

    override fun generateEarlyReturnQRCode(username: String, teacherName: String, startTime: LocalTime, reason: String) {
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

        val qrCode = EarlyReturnQRCode(username, teacherName, startTime, reason, byteArrayOutputStream.toByteArray())
        earlyReturnQRCodeRepository.save(qrCode)
    }
}
