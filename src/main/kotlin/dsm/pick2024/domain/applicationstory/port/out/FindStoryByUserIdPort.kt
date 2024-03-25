package dsm.pick2024.domain.applicationstory.port.out

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory
import java.util.UUID

interface FindStoryByUserIdPort {
    fun findAllByUserId(userId: UUID): List<ApplicationStory?>
}
