package dsm.pick2024.domain.mail.presentation

import dsm.pick2024.domain.mail.presentation.dto.request.SendMailRequest
import dsm.pick2024.domain.mail.presentation.dto.request.VerifyMailRequest
import dsm.pick2024.domain.mail.service.SendMailService
import dsm.pick2024.domain.mail.service.VerifyMailService
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
    @PostMapping("/send")
    fun sendMail(@RequestBody sendMailRequest: SendMailRequest) = sendMailService.execute(sendMailRequest)

    @PostMapping("/verify")
    fun verifyMail(@RequestBody verifyMailRequest: VerifyMailRequest) = verifyMailService.execute(verifyMailRequest)
}
