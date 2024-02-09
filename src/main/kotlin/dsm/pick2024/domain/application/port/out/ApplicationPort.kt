package dsm.pick2024.domain.application.port.out

interface ApplicationPort :
    ExistApplicationByUsernamePort,
    SaveApplicationPort,
    FindApplicationByIdPort,
    DeleteApplicationPort,
    QueryFloorApplicationPort
