package dsm.pick2024.domain.mail.service

import dsm.pick2024.domain.mail.exception.ExpiredMailCodeException
import dsm.pick2024.domain.mail.exception.MailCodeMissMatchException
import dsm.pick2024.domain.mail.port.`in`.VerifyMailUseCase
import dsm.pick2024.infrastructure.util.redis.port.RedisUtilPort
import org.springframework.stereotype.Service

@Service
class VerifyMailService(
    private val redisUtilPort: RedisUtilPort
) : VerifyMailUseCase {
    override fun execute(code: String, accountId: String) {
        val redisCode = redisUtilPort.getData(accountId) ?: throw ExpiredMailCodeException

        if (redisCode == code) {
            redisUtilPort.deleteData(accountId)
        } else {
            throw MailCodeMissMatchException
        }
    }
}
