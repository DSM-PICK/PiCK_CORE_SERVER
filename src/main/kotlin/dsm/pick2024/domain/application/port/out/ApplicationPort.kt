package dsm.pick2024.domain.application.port.out

interface ApplicationPort :
    ExistsApplicationPort,
    DeleteApplicationPort,
    SaveApplicationPort,
    QueryAllApplicationPort,
    QueryApplicationPort
