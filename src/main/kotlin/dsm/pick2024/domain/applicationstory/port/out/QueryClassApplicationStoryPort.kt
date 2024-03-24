package dsm.pick2024.domain.applicationstory.port.out

import dsm.pick2024.domain.applicationstory.domain.ApplicationStory

interface QueryClassApplicationStoryPort {
    fun findByGradeAndClassNum(
        grade: Int,
        classNum: Int
    ): List<ApplicationStory>
}
