package dsm.pick2024.domain.application.port.out

interface ApplicationPort :
    ExistApplicationByUsernamePort,
    SaveAllApplicationPort,
    FindApplicationByIdPort,
    DeleteApplicationPort,
    QueryFloorApplicationPort,
    QueryClassApplicationPort,
    SaveApplicationPort,
    QueryAllReasonApplicationPort
