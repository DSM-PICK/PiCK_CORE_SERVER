package dsm.pick2024.domain.applicationstory.persistence

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.mapper.ApplicationStoryMapper
import dsm.pick2024.domain.applicationstory.persistence.repository.ApplicationStoryRepository
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStoryPort
import org.springframework.stereotype.Component
import java.util.UUID
import kotlin.math.E

@Component
class ApplicationStoryPersistenceAdapter(
    private val applicationStoryMapper: ApplicationStoryMapper,
    private val applicationStoryRepository: ApplicationStoryRepository
) : ApplicationStoryPort {

    override fun saveAll(applicationStory: List<ApplicationStory>) {
        val entities = applicationStory.map { applicationStoryMapper.toEntity(it) }
        applicationStoryRepository.saveAll(entities)
    }

    override fun findAllByUserId(userId: UUID) =
        applicationStoryRepository.findAllByUserId(userId)
            .map { it.let { story -> applicationStoryMapper.toDomain(story) } }
            .sortedByDescending { it.date }
}
