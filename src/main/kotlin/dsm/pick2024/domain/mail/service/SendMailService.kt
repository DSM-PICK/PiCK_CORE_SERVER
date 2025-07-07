package dsm.pick2024.domain.mail.service

import dsm.pick2024.domain.mail.port.`in`.SendMailUseCase
import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest
import dsm.pick2024.domain.user.exception.DuplicateUserException
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.port.out.ExistsUserPort
import dsm.pick2024.global.security.jwt.exception.InternalServerErrorException
import dsm.pick2024.infrastructure.mail.MailProperties
import dsm.pick2024.infrastructure.util.redis.port.RedisUtilPort
import org.springframework.core.io.ClassPathResource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.springframework.util.FileCopyUtils
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.random.Random

@Service
class SendMailService(
    private val mailSender: JavaMailSender,
    private val redisUtilPort: RedisUtilPort,
    private val mailProperties: MailProperties,
) : SendMailUseCase {
    private val CODE_LENGTH = 6

    override fun execute(request: SendMailRequest) {

        val email = request.mail + mailProperties.dsmPostFix

        val authCode = generateAuthCode()
        redisUtilPort.setDataExpire(request.mail, authCode, 600)

        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")

        helper.setTo(email)
        helper.setSubject("[PiCK] ${request.title}")
        helper.setFrom(InternetAddress(mailProperties.username, "PiCK"))

        val template = loadEmailTemplate()
        val content = template
            .replace("\${authCode}", authCode)
            .replace("\${message}", request.message)
            .replace("\${title}", request.title)

        helper.setText(content, true)
        try {
            mailSender.send(message)
        } catch (e: Exception) {
            redisUtilPort.deleteData(email)
            throw InternalServerErrorException
        }
    }

    private fun generateAuthCode(): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..CODE_LENGTH)
            .map { chars[Random.nextInt(chars.length)] }
            .joinToString("")
    }

    private fun loadEmailTemplate(): String {
        val template = ClassPathResource("template/email_template.html")
        return String(FileCopyUtils.copyToByteArray(template.inputStream))
    }
}
