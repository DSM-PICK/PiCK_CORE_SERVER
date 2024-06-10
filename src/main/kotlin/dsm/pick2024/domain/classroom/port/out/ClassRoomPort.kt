package dsm.pick2024.domain.classroom.port.out

interface ClassRoomPort :
    ClassroomSavePort,
    DeleteClassRoomPort,
    ExistClassRoomPort,
    QueryClassroomPort
