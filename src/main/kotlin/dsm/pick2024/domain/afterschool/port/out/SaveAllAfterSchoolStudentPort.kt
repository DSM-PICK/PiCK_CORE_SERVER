package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.domain.AfterSchoolStudent

interface SaveAllAfterSchoolStudentPort {
    fun saveAll(afterSchool: List<AfterSchoolStudent>)
}
