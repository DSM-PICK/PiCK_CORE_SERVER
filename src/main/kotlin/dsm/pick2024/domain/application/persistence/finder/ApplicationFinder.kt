package dsm.pick2024.domain.application.persistence.finder

import dsm.pick2024.domain.application.domain.Application
import dsm.pick2024.domain.application.exception.ApplicationNotFoundException
import dsm.pick2024.domain.application.port.`in`.ApplicationFinderUseCase
import dsm.pick2024.domain.application.port.out.QueryApplicationPort
import org.springframework.stereotype.Component
import java.util.*

@Component
class ApplicationFinder(
    private val queryApplicationPort: QueryApplicationPort
) : ApplicationFinderUseCase {
    override fun findByIdOrThrow(id: UUID) = queryApplicationPort.findById(id) ?: throw ApplicationNotFoundException
    override fun findByUserIdOrThrow(id: UUID): Application {
        return queryApplicationPort.findByUserId(id) ?: throw ApplicationNotFoundException
    }
}
