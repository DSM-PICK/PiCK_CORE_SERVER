package dsm.pick2024.domain.earlyreturn.port.out

interface EarlyReturnPort :
    SaveAllEarlyReturnPort,
    ExistsEarlyReturnByUserIdPort,
    FindEarlyReturnByIdPort,
    DeleteEarlyReturnApplicationPort,
    SaveEarlyReturnPort,
    QueryClassEarlyReturnPort,
    QueryFloorEarlyReturnPort,
    DeleteAllEarlyReturnListPort,
    QueryAllEarlyReturnPort,
    FindEarlyReturnByUserIdPort,
    DeleteAllEarlyReturnPort,
    QueryOKMyEarlyReturn,
    ExistsOKEarlyReturnByUserIDPort,
    QueryAllEarlyReturnByStatusPort
