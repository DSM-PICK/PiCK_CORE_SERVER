package dsm.pick2024.domain.classroom.port.out

interface ClassroomPort :
    ClassroomSavePort,
    ClassroomDeletePort,
    FindByUsernamePort,
    ExistsByUsernamePort,
    QueryFloorClassroomPort,
    QueryGradeClassroomPort
