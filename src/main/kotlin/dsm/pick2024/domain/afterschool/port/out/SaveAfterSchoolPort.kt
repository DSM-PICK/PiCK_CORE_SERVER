package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.domain.AfterSchool

interface SaveAfterSchoolPort {
    fun save(afterSchool: AfterSchool)
}
