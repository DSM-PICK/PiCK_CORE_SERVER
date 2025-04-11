package dsm.pick2024.domain.applicationstory.port.out

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory

interface SaveApplicationStoryPort {
    fun save(applicationStory: ApplicationStory)
}
