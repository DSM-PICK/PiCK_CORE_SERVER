package dsm.pick2024.domain.applicationstory.port.out

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory

interface FindStoryByUsernamePort {
    fun findByUsername(username: String): List<ApplicationStory>?
}
