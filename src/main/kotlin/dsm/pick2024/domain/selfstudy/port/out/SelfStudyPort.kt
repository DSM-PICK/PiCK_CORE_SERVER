package dsm.pick2024.domain.selfstudy.port.out

interface SelfStudyPort :
    SelfStudySavePort,
    SelfStudyByDateAndFloor,
    FindByDaySelfStudyTeacherPort,
    FindByMonthSelfStudyTeacherPort,
    FindByDatePort,
    SelfStudySaveAllPort,
    FindByTodaySelfStudyTeacherPort
