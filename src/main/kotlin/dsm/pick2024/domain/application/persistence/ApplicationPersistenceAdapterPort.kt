package dsm.pick2024.domain.application.persistence

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.mapper.ApplicationMapper
import dsm.pick2024.domain.application.persistence.repository.ApplicationRepository
import dsm.pick2024.domain.application.port.out.ApplicationPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationPersistenceAdapterPort(
    private val applicationRepository: ApplicationRepository,
    private val applicationMapper: ApplicationMapper
) : ApplicationPort {
    override fun save(application: Application) {
        applicationRepository.save(applicationMapper.toEntity(application))
    }

    override fun existsByUsername(username: String) = applicationRepository.existsByUsername(username)

    override fun findById(id: UUID) =
        applicationRepository.findById(id).let { applicationMapper.toDomain(it) }

    override fun deleteById(applicationId: UUID) {
        applicationRepository.deleteById(applicationId)
    }
}
