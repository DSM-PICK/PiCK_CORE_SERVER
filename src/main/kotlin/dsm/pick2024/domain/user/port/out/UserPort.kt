package dsm.pick2024.domain.user.port.out

interface UserPort :
    QueryUserPort,
    ExistsUserPort,
    UserSavePort,
    UpdateUserPort,
    DeleteUserPort
