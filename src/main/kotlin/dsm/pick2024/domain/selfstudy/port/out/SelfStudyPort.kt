package dsm.pick2024.domain.selfstudy.port.out

interface SelfStudyPort :
    SelfStudySavePort,
    SelfStudyByDateAndFloorPort,
    FindByDaySelfStudyTeacherPort,
    FindByMonthSelfStudyTeacherPort,
    FindByDatePort,
    SelfStudySaveAllPort,
    FindByTodaySelfStudyTeacherPort,
    DeleteByDatePort
