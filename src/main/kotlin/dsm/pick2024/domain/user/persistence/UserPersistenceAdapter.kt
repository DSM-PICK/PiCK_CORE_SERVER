package dsm.pick2024.domain.user.persistence

import dsm.pick2024.domain.user.domain.User
import dsm.pick2024.domain.user.mapper.UserMapper
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import dsm.pick2024.domain.user.port.out.UserByNamePort
import dsm.pick2024.domain.user.port.out.UserPort
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : UserPort {
    override fun findByName(name: String): User? =
        userRepository.findByName(name)?.let { userMapper.toDomain(it) }

    override fun findByAccountId(accountId: String): User? =
        userRepository.findByAccountId(accountId)?.let { userMapper.toDomain(it) }

}
