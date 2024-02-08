package dsm.pick2024.domain.applicationstory.persistence

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import dsm.pick2024.domain.applicationstory.mapper.ApplicationStoryMapper
import dsm.pick2024.domain.applicationstory.persistence.repository.ApplicationStoryRepository
import dsm.pick2024.domain.applicationstory.port.out.ApplicationStoryPort
import dsm.pick2024.domain.classroom.domain.Classroom
import org.springframework.stereotype.Component

@Component
class ApplicationStoryPersistenceAdapter(
    private val applicationStoryMapper: ApplicationStoryMapper,
    private val applicationStoryRepository: ApplicationStoryRepository
) : ApplicationStoryPort {

    override fun save(applicationStory: ApplicationStory) {
        applicationStoryRepository.save(applicationStoryMapper.toEntity(applicationStory))
    }

    override fun findByUsername(username: String) =
        applicationStoryRepository.findByUsername(username).map { applicationStoryMapper.toDomain(it) }

}
