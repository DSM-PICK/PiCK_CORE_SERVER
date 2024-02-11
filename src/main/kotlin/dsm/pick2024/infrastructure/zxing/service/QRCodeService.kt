package dsm.pick2024.infrastructure.zxing.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.infrastructure.zxing.entity.QRCode
import dsm.pick2024.infrastructure.zxing.repository.QRCodeRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import java.util.*
import javax.imageio.ImageIO

@Service
class QRCodeService(
    private val qrCodeRepository: QRCodeRepository
) {

    fun generateQRCode(username: String, teacherName: String, startTime: LocalTime, endTime: LocalTime?) {
        val width = 200
        val height = 200

        val byteArrayOutputStream = ByteArrayOutputStream()

        var message = "username=$username&teacherName=$teacherName&startTime=$startTime"
        if (endTime != null) {
            message += "&endTime=$endTime"
        }

        val code = QRCodeWriter().encode(
            message,
            BarcodeFormat.QR_CODE,
            width,
            height,
            mapOf(EncodeHintType.MARGIN to 1)
        )

        ImageIO.write(MatrixToImageWriter.toBufferedImage(code), "png", byteArrayOutputStream)


        val id = UUID.randomUUID()
        val qrCode = QRCode(id, username, teacherName, startTime, endTime, byteArrayOutputStream.toByteArray())
        qrCodeRepository.save(qrCode)
    }
}
