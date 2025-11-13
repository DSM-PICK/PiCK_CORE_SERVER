package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.port.`in`.UserDeleteUseCase
import dsm.pick2024.domain.user.port.out.DeleteUserPort
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
class UserDeleteService(
    private val deleteUserPort: DeleteUserPort
) : UserDeleteUseCase {

    @Transactional
    override fun delete() {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw RuntimeException("인증 정보를 찾을 수 없습니다.")

        val userIdString = authentication.name
        val userId = try {
            UUID.fromString(userIdString)
        } catch (e: Exception) {
            throw RuntimeException("유효하지 않은 사용자 ID입니다.")
        }

        deleteUserPort.deleteById(userId)
    }
}
