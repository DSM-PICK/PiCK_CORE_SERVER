package dsm.pick2024.domain.user.port.out

interface UserPort :
    FindByAccountIdPort,
    FindByNamePort,
    UserAllPort
