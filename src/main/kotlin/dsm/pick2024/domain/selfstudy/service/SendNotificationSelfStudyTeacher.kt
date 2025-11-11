package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.port.`in`.AdminFinderUseCase
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import dsm.pick2024.domain.selfstudy.port.`in`.SelfStudyFinderUseCase
import dsm.pick2024.domain.selfstudy.port.`in`.SendNotificationSelfStudyTeacherUseCase
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SendNotificationSelfStudyTeacher(
    private val adminFinderUseCase: AdminFinderUseCase,
    private val selfStudyFinderUseCase: SelfStudyFinderUseCase,
    private val fcmSendPort: FcmSendPort
) : SendNotificationSelfStudyTeacherUseCase {
    override fun execute() {
        val selfStudies = selfStudyFinderUseCase.findByDaySelfStudyOrThrow(LocalDate.now()).map { it }

        selfStudies.map {
            val admin = adminFinderUseCase.findByAdminNameOrThrow(it.teacherName)
            admin.deviceToken?.let {
                    token ->
                fcmSendPort.send(
                    deviceToken = token,
                    title = "[PiCK] 자습감독 알림",
                    body = "${admin.name}선생님은 오늘 ${it.floor}층 자습감독 선생님입니다."
                )
            }
        }
    }
}
