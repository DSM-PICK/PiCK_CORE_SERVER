package dsm.pick2024.domain.status.port.out

interface StatusPort :
    SaveAllStatusPort,
    QueryClassStatusPort,
    FindStatusByUserId,
    SaveStatusPort,
    FindAllStatusPort
