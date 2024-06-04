package dsm.pick2024.domain.selfstudy.port.out

interface SelfStudyPortPort :
    SelfStudySavePort,
    SelfStudyByDateAndFloorPort,
    FindByDaySelfStudyTeacherPort,
    FindByMonthSelfStudyTeacherPort,
    FindByDatePort,
    SelfStudySaveAllPort,
    FindByTodaySelfStudyTeacherPort,
    DeleteByDatePort
