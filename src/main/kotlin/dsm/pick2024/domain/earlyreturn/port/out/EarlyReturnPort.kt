package dsm.pick2024.domain.earlyreturn.port.out

interface EarlyReturnPort :
    SaveAllEarlyReturnPort,
    ExistsEarlyReturnPort,
    DeleteEarlyReturnPort,
    QueryEarlyReturnPort,
    SaveEarlyReturnPort,
    QueryAllEarlyReturnPort,
    QueryAllEarlyReturnByStatusPort
