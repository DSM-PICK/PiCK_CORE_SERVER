package dsm.pick2024.domain.mail.presentation

import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest
import dsm.pick2024.domain.mail.service.MailCheckCodeService
import dsm.pick2024.domain.mail.service.SendMailService
import dsm.pick2024.domain.user.presentation.dto.request.UserCheckCodeRequest
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val sendMailService: SendMailService,
    private val mailCheckCodeService: MailCheckCodeService
) {
    @Operation(summary = "메일 전송 api")
    @PostMapping("/send")
    fun sendMail(@RequestBody sendMailRequest: SendMailRequest) = sendMailService.execute(sendMailRequest)

    @Operation(summary = "메일에 발송된 코드가 맞는지 확인")
    @PostMapping("/check")
    fun checkMail(@RequestBody request: UserCheckCodeRequest) = mailCheckCodeService.execute(request)
}
