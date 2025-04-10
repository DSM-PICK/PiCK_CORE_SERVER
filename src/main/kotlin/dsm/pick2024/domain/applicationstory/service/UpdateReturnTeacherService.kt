package dsm.pick2024.domain.applicationstory.service

import dsm.pick2024.domain.admin.port.`in`.AdminFacadeUseCase
import dsm.pick2024.domain.application.port.`in`.UpdateReturnTeacherUseCase
import dsm.pick2024.domain.applicationstory.port.out.QueryAllApplicationStoryPort
import dsm.pick2024.domain.applicationstory.port.out.SaveApplicationStoryPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateReturnTeacherService(
    private val adminFacadeUseCase: AdminFacadeUseCase,
    private val saveApplicationStoryPort: SaveApplicationStoryPort,
    private val queryAllApplicationStoryPort: QueryAllApplicationStoryPort
) : UpdateReturnTeacherUseCase {
    override fun updateReturnTeacher(userId: UUID) {
        val admin = adminFacadeUseCase.currentAdmin()
        val applicationStoryList = queryAllApplicationStoryPort.findAllByUserId(userId)

        val latestApplicationStory = applicationStoryList?.let { it.maxByOrNull { it.date } }

        latestApplicationStory?.let { saveApplicationStoryPort.save(it.updateReturnTeacher(admin.name)) }
    }
}
