package dsm.pick2024.domain.selfstudy.service

import dsm.pick2024.domain.admin.exception.AdminNotFoundException
import dsm.pick2024.domain.admin.port.out.QueryAdminPort
import dsm.pick2024.domain.fcm.port.out.FcmSendPort
import dsm.pick2024.domain.selfstudy.port.`in`.SendNotificationSelfStudyTeacherUseCase
import dsm.pick2024.domain.selfstudy.port.out.SelfStudyPort
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SendNotificationSelfStudyTeacher(
    private val queryAdminPort: QueryAdminPort,
    private val selfStudyPort: SelfStudyPort,
    private val fcmSendPort: FcmSendPort
) : SendNotificationSelfStudyTeacherUseCase {
    override fun execute() {
        val teacher = selfStudyPort.findByDaySelfStudy(LocalDate.now()).map { it }

        teacher.map {
            val admin = queryAdminPort.findBYAdminByName(it.teacherName) ?: throw AdminNotFoundException
            admin.map { a ->
                if (a.deviceToken != null) {
                    fcmSendPort.send(
                        deviceToken = a.deviceToken,
                        title = "[PiCK] 자습감독 알림",
                        body = "${a.name}선생님은 오늘 ${it.floor}층 자습감독 선생님입니다."
                    )
                }
            }
        }
    }
}
