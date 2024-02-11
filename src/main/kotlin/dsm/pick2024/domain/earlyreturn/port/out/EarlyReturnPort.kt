package dsm.pick2024.domain.earlyreturn.port.out

interface EarlyReturnPort :
    SaveAllEarlyReturnPort,
    ExistsEarlyReturnByUsernamePort,
    FindEarlyReturnByIdPort,
    DeleteEarlyReturnApplicationPort,
    SaveEarlyReturnPort,
    QueryClassEarlyReturnPort,
    QueryFloorEarlyReturnPort,
    DeleteAllEarlyReturnListPort,
    QueryAllEarlyReturnPort,
    FindEarlyReturnByNamePort,
    DeleteAllEarlyReturnPort
