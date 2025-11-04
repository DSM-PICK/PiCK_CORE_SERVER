package dsm.pick2024.domain.applicationstory.persistence

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.mapper.ApplicationStoryMapper
import dsm.pick2024.domain.applicationstory.persistence.repository.ApplicationStoryRepository
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStoryPort
import dsm.pick2024.domain.user.exception.UserNotFoundException
import dsm.pick2024.domain.user.persistence.repository.UserRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ApplicationStoryPersistenceAdapter(
    private val applicationStoryMapper: ApplicationStoryMapper,
    private val applicationStoryRepository: ApplicationStoryRepository,
    private val userRepository: UserRepository
) : ApplicationStoryPort {

    override fun saveAll(applicationStory: List<ApplicationStory>) {
        val userIds = applicationStory.map { it.userId }
        val users = userRepository.findAllByIdIn(userIds).associateBy { it.id }

        val entities = applicationStory.map {
            val user = users[it.userId] ?: throw UserNotFoundException
            applicationStoryMapper.toEntity(it, user)
        }

        applicationStoryRepository.saveAll(entities)
    }

    override fun findAllByUserId(userId: UUID) =
        applicationStoryRepository.findAllByUserId(userId)
            .map { it.let { story -> applicationStoryMapper.toDomain(story) } }
            .sortedByDescending { it.date }

    override fun save(applicationStory: ApplicationStory) {
        val user = userRepository.findById(applicationStory.userId) ?: throw UserNotFoundException
        applicationStoryRepository.save(applicationStoryMapper.toEntity(applicationStory, user))
    }
}
