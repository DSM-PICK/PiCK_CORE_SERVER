package dsm.pick2024.domain.application.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.enums.ApplicationStatus
import dsm.pick2024.domain.application.enums.Status
import dsm.pick2024.domain.applicationstory.enums.Type
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.StatusOKApplicationUseCase
import dsm.pick2024.domain.application.port.out.FindApplicationByIdPort
import dsm.pick2024.domain.application.port.out.SaveAllApplicationPort
import dsm.pick2024.domain.application.presentation.dto.request.StatusApplicationRequest
import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStorySavePort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.ByteArrayOutputStream
import java.time.LocalTime
import javax.imageio.ImageIO

@Service
class StatusOKApplicationService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val findApplicationByIdPort: FindApplicationByIdPort,
    private val saveAllApplicationPort: SaveAllApplicationPort,
    private val applicationStorySaveAllPort: ApplicationStorySavePort,
) : StatusOKApplicationUseCase {

    @Transactional
    override fun statusOKApplication(request: StatusApplicationRequest?) {
        val admin = adminFacadeUseCase.currentUser()

        val applicationUpdate = mutableListOf<Application>()
        val applicationStory = mutableListOf<ApplicationStory>()

        for (id in request!!.ids) {
            val application = findApplicationByIdPort.findById(id) ?: throw ApplicationNotFoundException

            val image =generateApplicationQRCode(
                application.username,
                application.teacherName!!,
                application.startTime,
                application.endTime,
                application.reason
            )

            val updatedApplication = application.copy(
                teacherName = admin.name,
                status = Status.OK,
                applicationStatus = ApplicationStatus.NON_RETURN,
                image = image
            )
            applicationUpdate.add(updatedApplication)

            val applicationStorySave = ApplicationStory(
                reason = updatedApplication.reason,
                username = updatedApplication.username,
                startTime = updatedApplication.startTime,
                endTime = updatedApplication.endTime,
                date = updatedApplication.date,
                type = Type.APPLICATION
            )
            applicationStory.add(applicationStorySave)
        }

        saveAllApplicationPort.saveAll(applicationUpdate)
        applicationStorySaveAllPort.saveAll(applicationStory)
    }

    override fun generateApplicationQRCode(
        username: String,
        teacherName: String,
        startTime: LocalTime,
        endTime: LocalTime,
        reason: String
    ): ByteArray {
        val width = 200
        val height = 200

        val byteArrayOutputStream = ByteArrayOutputStream()

        val message = "username=$username&teacherName=$teacherName&startTime=$startTime&endTIme=$endTime"

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
