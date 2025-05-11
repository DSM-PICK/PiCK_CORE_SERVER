package dsm.pick2024.domain.mail.presentation

import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest
import dsm.pick2024.domain.mail.presentation.dto.request.VerifyMailRequest
import dsm.pick2024.domain.mail.service.SendMailService
import dsm.pick2024.domain.mail.service.VerifyMailService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val sendMailService: SendMailService,
    private val verifyMailService: VerifyMailService
) {
    @Operation(summary = "메일 전송 api")
    @PostMapping("/send")
    fun sendMail(@RequestBody sendMailRequest: SendMailRequest) = sendMailService.execute(sendMailRequest)

    @Operation(summary = "메일 인증코드 확인 api")
    @PostMapping("/verify")
    fun verifyMail(@RequestBody verifyMailRequest: VerifyMailRequest) = verifyMailService.execute(verifyMailRequest)
}
