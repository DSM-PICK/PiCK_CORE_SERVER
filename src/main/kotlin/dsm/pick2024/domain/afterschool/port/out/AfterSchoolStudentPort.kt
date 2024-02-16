package dsm.pick2024.domain.afterschool.port.out

import dsm.pick2024.domain.afterschool.service.DeleteAfterSchoolStudentService
import dsm.pick2024.domain.application.port.out.DeleteAllApplicationPort

interface AfterSchoolStudentPort :
    SaveAfterSchoolStudentPort,
    DeleteAfterSchoolStudentPort,
    QueryAfterSchoolStudentAllPort
