package dsm.pick2024.domain.application.port.out

interface ApplicationByStatusPort :
    ExistApplicationByUserIdPort,
    SaveAllApplicationPort,
    FindApplicationByIdPort,
    DeleteApplicationPort,
    QueryFloorApplicationPort,
    QueryClassApplicationPort,
    SaveApplicationPort,
    QueryAllApplicationPort,
    FindApplicationByUserIdPort,
    DeleteAllApplicationPort,
    QueryOKMyApplication,
    ExistsOKApplicationByUserIdPort,
    QueryAllApplicationByStatusPort
