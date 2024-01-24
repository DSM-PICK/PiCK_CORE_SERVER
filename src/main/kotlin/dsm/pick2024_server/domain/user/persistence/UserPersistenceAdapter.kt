package dsm.pick2024_server.domain.user.persistence

import dsm.pick2024_server.domain.user.domain.User
import dsm.pick2024_server.domain.user.mapper.UserMapper
import dsm.pick2024_server.domain.user.persistence.repository.UserRepository
import dsm.pick2024_server.domain.user.port.out.UserByNamePort
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
): UserByNamePort {
    override fun findByName(name: String): User? =
        userRepository.findByName(name)?.let { userMapper.toDomain(it) }

}
