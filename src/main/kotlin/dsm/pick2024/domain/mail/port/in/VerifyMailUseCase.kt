package dsm.pick2024.domain.mail.port.`in`

interface VerifyMailUseCase {
    fun execute(code: String, accountId: String)
}
