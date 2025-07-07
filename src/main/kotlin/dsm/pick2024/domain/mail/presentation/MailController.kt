package dsm.pick2024.domain.mail.presentation

import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest
import dsm.pick2024.domain.mail.service.SendMailService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(
    private val sendMailService: SendMailService
) {
    @Operation(summary = "메일 전송 api")
    @PostMapping("/send")
    fun sendMail(@RequestBody sendMailRequest: SendMailRequest) = sendMailService.execute(sendMailRequest)
}
