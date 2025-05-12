package dsm.pick2024.domain.mail.service

import dsm.pick2024.domain.mail.exception.ExpiredMailCodeException
import dsm.pick2024.domain.mail.exception.MailCodeMissMatchException
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.domain.mail.presentation.dto.request.VerifyMailRequest
import dsm.pick2024.infrastructure.util.port.RedisUtilPort
import org.springframework.stereotype.Service

@Service
class VerifyMailService(
    private val redisUtilPort: RedisUtilPort
) : VerifyMailUseCase {
    override fun execute(request: VerifyMailRequest): Boolean {
        val redisCode = redisUtilPort.getData(request.mail) ?: throw ExpiredMailCodeException

        return if (redisCode == request.code) {
            redisUtilPort.deleteData(request.mail)
            true
        } else {
            throw MailCodeMissMatchException
        }
    }
}
