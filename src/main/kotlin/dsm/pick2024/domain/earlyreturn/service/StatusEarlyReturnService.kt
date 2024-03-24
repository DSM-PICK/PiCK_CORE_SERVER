package dsm.pick2024.domain.earlyreturn.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import dsm.pick2024.domain.earlyreturn.domain.EarlyReturn
import dsm.pick2024.domain.earlyreturn.exception.EarlyReturnApplicationNotFoundException
import dsm.pick2024.domain.earlyreturn.port.`in`.StatusEarlyReturnUseCase
import dsm.pick2024.domain.earlyreturn.port.out.DeleteAllEarlyReturnListPort
import dsm.pick2024.domain.earlyreturn.port.out.FindEarlyReturnByIdPort
import dsm.pick2024.domain.earlyreturn.port.out.SaveAllEarlyReturnPort
import dsm.pick2024.domain.earlyreturn.presentation.dto.request.StatusEarlyReturnRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import java.util.Base64
import javax.imageio.ImageIO

@Service
class StatusEarlyReturnService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveAllEarlyReturnPort: SaveAllEarlyReturnPort,
    private val findEarlyReturnByIdPort: FindEarlyReturnByIdPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort,
    private val deleteAllEarlyReturnListPort: DeleteAllEarlyReturnListPort
) : StatusEarlyReturnUseCase {
    @Transactional
    override fun statusEarlyReturn(request: StatusEarlyReturnRequest) {
        val admin = adminFacadeUseCase.currentUser()

        val earlyReturnUpdate = mutableListOf<EarlyReturn>()
        val applicationStory = mutableListOf<ApplicationStory>()

        if (request.status == Status.NO) {
            for (id in request.ids) {
                val earlyReturn =
                    findEarlyReturnByIdPort.findById(id)
                        ?: throw EarlyReturnApplicationNotFoundException
                earlyReturnUpdate.add(earlyReturn)
            }
            deleteAllEarlyReturnListPort.deleteAll(earlyReturnUpdate)
        }

        for (earlyReturnId in request.ids) {
            val earlyReturn =
                findEarlyReturnByIdPort.findById(earlyReturnId)
                    ?: throw EarlyReturnApplicationNotFoundException

            val image =
                generateEarlyReturnQRCode(
                    earlyReturn.username,
                    admin.name,
                    earlyReturn.startTime,
                    earlyReturn.reason
                )

            val updateEarlyReturn =
                earlyReturn.copy(
                    teacherName = admin.name,
                    status = Status.OK,
                    image = image.toString()
                )
            earlyReturnUpdate.add(updateEarlyReturn)

            val applicationStorySave =
                ApplicationStory(
                    reason = earlyReturn.reason,
                    username = earlyReturn.username,
                    startTime = earlyReturn.startTime,
                    date = earlyReturn.date,
                    type = Type.EARLY_RETURN,
                    userId = updateEarlyReturn.userId
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
    ): String {
        val width = 200
        val height = 200

        val byteArrayOutputStream = ByteArrayOutputStream()

        val message = "username=$username&teacherName=$teacherName&startTime=$startTime"

        val code =
            QRCodeWriter().encode(
                message,
                BarcodeFormat.QR_CODE,
                width,
                height,
                mapOf(EncodeHintType.MARGIN to 1)
            )

        ImageIO.write(MatrixToImageWriter.toBufferedImage(code), "png", byteArrayOutputStream)

        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
    }
}
