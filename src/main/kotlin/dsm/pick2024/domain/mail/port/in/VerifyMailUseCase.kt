package dsm.pick2024.domain.mail.port.`in`

interface VerifyMailUseCase {
    fun verifyAndConsume(code: String, accountId: String)

    fun verifyOnly(code: String, accountId: String): Boolean
}
