package dsm.pick2024.domain.user.service

import dsm.pick2024.domain.user.facade.UserFacade
import dsm.pick2024.domain.user.port.`in`.UserDeleteUseCase
import dsm.pick2024.domain.user.port.out.DeleteUserPort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserDeleteService(
    private val deleteUserPort: DeleteUserPort,
    private val userFacade: UserFacade
) : UserDeleteUseCase {

    @Transactional
    override fun delete() {
        val user = userFacade.currentUser()
        deleteUserPort.deleteById(user.id)
    }
}
