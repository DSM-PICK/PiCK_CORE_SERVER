package dsm.pick2024.domain.application.port.out

interface ApplicationByStatusPortPort :
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
    QueryOKMyApplicationPort,
    ExistsOKApplicationByUserIdPort,
    QueryAllApplicationByStatusPort
