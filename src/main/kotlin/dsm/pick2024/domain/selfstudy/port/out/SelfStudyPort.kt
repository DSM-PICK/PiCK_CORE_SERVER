package dsm.pick2024.domain.selfstudy.port.out

interface SelfStudyPort :
    SelfStudySavePort,
    SelfStudyByDateAndFloor,
    ExistsByDateAndFloor,
    FindByTodaySelfStudyTeacherPort,
    FindByMonthSelfStudyTeacherPort,
    FindByDatePort,
    SelfStudySaveAllPort
