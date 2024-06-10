package dsm.pick2024.domain.earlyreturn.port.out

interface EarlyReturnPort :
    SaveAllEarlyReturnPort,
    ExistsEarlyReturnByUserIdPort,
    FindEarlyReturnByIdPort,
    DeleteEarlyReturnPort,
    SaveEarlyReturnPort,
    QueryClassEarlyReturnPort,
    QueryFloorEarlyReturnPort,
    QueryAllEarlyReturnPort,
    FindEarlyReturnByUserIdPort,
    QueryOKMyEarlyReturn,
    ExistsOKEarlyReturnByUserIDPort,
    QueryAllEarlyReturnByStatusPort
