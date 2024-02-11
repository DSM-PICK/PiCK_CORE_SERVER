package dsm.pick2024.domain.application.port.out

interface EarlyReturnPort :
    SaveAllEarlyReturnPort,
    ExistsEarlyReturnByUsernamePort,
    FindEarlyReturnByIdPort,
    DeleteEarlyReturnApplicationPort,
    SaveEarlyReturnPort,
    QueryAllEarlyReturnPort
