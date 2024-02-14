package dsm.pick2024.domain.earlyreturn.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.earlyreturn.port.`in`.StatusOKEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.earlyreturn.port.out.SaveAllEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import javax.imageio.ImageIO

@Service
class StatusOKEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveAllEarlyReturnPort: SaveAllEarlyReturnPort,
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort
) : StatusOKEarlyReturnUseCase {

    @Transactional
    override fun statusOKEarlyReturn(request: StatusEarlyReturnRequest?) {
        val admin = adminFacadeUseCase.currentUser()

        val earlyReturnUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (earlyReturnId in request!!.earlyReturnIds) {
            val earlyReturn = findEarlyReturnByIdPort.findById(earlyReturnId)
                ?: throw EarlyReturnApplicationNotFoundException

            val image = generateEarlyReturnQRCode(
                earlyReturn.username,
                earlyReturn.teacherName!!,
                earlyReturn.startTime,
                earlyReturn.reason
            )

            val updateEarlyReturn = earlyReturn.copy(
                teacherName = admin.name,
                status = Status.OK,
                image = image,
                type = Type.EARLY_RETURN
            )
            earlyReturnUpdate.add(updateEarlyReturn)

            val applicationStorySave = ApplicationStory(
                reason = earlyReturn.reason,
                username = earlyReturn.username,
                startTime = earlyReturn.startTime,
                date = earlyReturn.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)
        }

        saveAllEarlyReturnPort.saveAll(earlyReturnUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }

    override fun generateEarlyReturnQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        reason: String
    ): ByteArray {
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

        return byteArrayOutputStream.toByteArray()
    }
}
