package dsm.pick2024.domain.user.port.out

interface UserStudentNumPort :
    FindByAccountIdPort,
    FindByStudentNumPort,
    UserAllPort,
    ExistsByAccountIdPort,
    UserSavePort
