package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.entity.AfterSchoolStudentJpaEntity

interface SaveAllAfterSchoolStudentPort {
    fun saveAll(afterSchool: MutableList<AfterSchoolStudentJpaEntity>)
}
