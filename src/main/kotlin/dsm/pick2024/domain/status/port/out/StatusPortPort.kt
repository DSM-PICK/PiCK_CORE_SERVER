package dsm.pick2024.domain.status.port.out

interface StatusPortPort :
    SaveAllStatusPort,
    QueryClassStatusPort,
    FindStatusByUserIdPort,
    SaveStatusPort,
    FindAllStatusPort
