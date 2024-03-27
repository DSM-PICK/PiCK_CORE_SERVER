package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent

interface SaveAfterSchoolStudentPort {
    fun save(afterSchoolStudent: AfterSchoolStudent)
}
